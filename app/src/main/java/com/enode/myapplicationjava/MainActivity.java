package com.enode.myapplicationjava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * @author 83754
 */
public class MainActivity extends AppCompatActivity {

  private TextView phoneText;
  private TextView operationSteps;
  private Button goSeetings;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    EventBus.getDefault().register(this);

    phoneText = findViewById(R.id.phoneBrand);
    operationSteps = findViewById(R.id.operationStepsText);
    goSeetings=findViewById(R.id.button);
    goSeetings.setOnClickListener(this::goSeetings);

    String text = ElegantKeepAliveUtils.getPhoneBrand();
    KLog.e("返回的手机品牌：" + text);
    phoneText.setText(text);

    if (ElegantKeepAliveUtils.isOppo()) {
      KLog.e("当前手机为OPPO手机");
      ElegantKeepAliveUtils.goOppoSetting(this);
    }
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void phoneBrand(PhoneEvent event) {
    int code = event.getCode();
    KLog.e("事件类型：" + code);
    switch (code) {
      case 0:
        break;
      case 1:
        KLog.e("要显示的信息: " + event.getPhone());
        operationSteps.setText(event.getPhone());
        break;
      default:
    }
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    EventBus.getDefault().unregister(this);
  }

  public void goSeetings(View view) {
    ElegantKeepAliveUtils.goPhoneSeetings(this);
  }
}