package com.enode.myapplicationjava;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;

/**
 * 申请后台启动白名单工具
 *
 * @author pan
 * @date 2022/02/16/012.
 */
public class ElegantKeepAliveUtils {
  // 首先在清单添加 <uses-permission android:name="android.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS" />

  /**
   * 原生系统判断app是否在后台运行白名单中
   *
   * @return 结果
   */
  @RequiresApi(api = Build.VERSION_CODES.M)
  private boolean isIgnoringBatteryOptimizations(Context context) {
    boolean isIgnoring = false;
    PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
    if (powerManager != null) {
      isIgnoring = powerManager.isIgnoringBatteryOptimizations(context.getPackageName());
    }
    return isIgnoring;
  }

  /**
   * 申请加入白名单
   */
  @RequiresApi(api = Build.VERSION_CODES.M)
  public void requestIgnoreBatteryOptimizations(Context context) {
    try {
      Intent intent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
      intent.setData(Uri.parse("package:" + context.getPackageName()));
      context.startActivity(intent);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 跳转到指定应用的首页
   */
  private static void showActivity(Context context, @NonNull String packageName) {
    Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
    context.startActivity(intent);
  }

  /**
   * 跳转到指定应用的指定页面
   */
  private static void showActivity(Context context, @NonNull String packageName, @NonNull String activityDir) {
    Intent intent = new Intent();
    intent.setComponent(new ComponentName(packageName, activityDir));
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    context.startActivity(intent);
  }

  /**
   * 获取当前手机品牌
   *
   * @return 手机品牌
   */
  public static String getPhoneBrand() {
    String brand = Build.BRAND.toLowerCase();
    switch (brand) {
      case Const.PHOME_BRAND_HUAWEI:
        EventBus.getDefault().post(new PhoneEvent(1, "应用启动管理 -> 关闭应用开关 -> 打开允许自启动"));
//        goHuaweiSetting(context);
        brand = "华为手机";
        break;
      case Const.PHOME_BRAND_HONOR:
        EventBus.getDefault().post(new PhoneEvent(1, "应用启动管理 -> 关闭应用开关 -> 打开允许自启动"));
//        goHuaweiSetting(context);
        brand = "荣耀手机";
        break;
      case Const.PHOME_BRAND_XIAOMI:
        EventBus.getDefault().post(new PhoneEvent(1, "授权管理 -> 自启动管理 -> 允许应用自启动"));
//        goXiaomiSetting(context);
        brand = "小米手机";
        break;
      case Const.PHOME_BRAND_OPPO:
        EventBus.getDefault().post(new PhoneEvent(1, "权限隐私 -> 自启动管理 -> 允许应用自启动"));
//        goOppoSetting(context);
        brand = "OPPO手机";
        break;
      case Const.PHOME_BRAND_VIVO:
        EventBus.getDefault().post(new PhoneEvent(1, "权限管理 -> 自启动 -> 允许应用自启动"));
//        goVivoSetting(context);
        brand = "vivo手机";
        break;
      case Const.PHOME_BRAND_MEIZU:
        EventBus.getDefault().post(new PhoneEvent(1, "权限管理 -> 后台管理 -> 点击应用 -> 允许后台运行"));
//        goMeizuSetting(context);
        brand = "魅族手机";
        break;
      case Const.PHOME_BRAND_SAMSUNG:
        EventBus.getDefault().post(new PhoneEvent(1, "自动运行应用程序 -> 打开应用开关 -> 电池管理 -> 未监视的应用程序 -> 添加应用"));
//        goSamsungSetting(context);
        brand = "三星手机";
        break;
      case Const.PHOME_BRAND_LETV:
        EventBus.getDefault().post(new PhoneEvent(1, "自启动管理 -> 允许应用自启动"));
//        goLetvSetting(context);
        brand = "乐视手机";
        break;
      case Const.PHOME_BRAND_SMARTISAN:
        EventBus.getDefault().post(new PhoneEvent(1, "权限管理 -> 自启动权限管理 -> 点击应用 -> 允许被系统启动"));
//        goSmartisanSetting(context);
        brand = "锤子手机";
        break;
      default:
        EventBus.getDefault().post(new PhoneEvent(0, "未知手机，需要自主查看设置操作"));
        return brand;
    }
    return brand;
  }

  /**
   *
   * 调整到指定手机自启动管理界面
   */
  public static void goPhoneSeetings(Context context) {
    String brand = Build.BRAND.toLowerCase();
    KLog.e("手机品牌：" + brand);
    switch (brand) {
      case Const.PHOME_BRAND_HUAWEI:
        goHuaweiSetting(context);
        break;
      case Const.PHOME_BRAND_HONOR:
        goHuaweiSetting(context);
        break;
      case Const.PHOME_BRAND_XIAOMI:
        goXiaomiSetting(context);
        break;
      case Const.PHOME_BRAND_OPPO:
        goOppoSetting(context);
        break;
      case Const.PHOME_BRAND_VIVO:
        goVivoSetting(context);
        break;
      case Const.PHOME_BRAND_MEIZU:
        goMeizuSetting(context);
        break;
      case Const.PHOME_BRAND_SAMSUNG:
        goSamsungSetting(context);
        break;
      case Const.PHOME_BRAND_LETV:
        goLetvSetting(context);
        break;
      case Const.PHOME_BRAND_SMARTISAN:
        goSmartisanSetting(context);
        break;
      default:
        break;
    }
  }


  // 下面是主流手机厂商判断，及跳转方法和对应设置步骤

  /**
   * 判断是否是华为
   *
   * @return 结果
   */
  public boolean isHuawei() {
    if (Build.BRAND == null) {
      return false;
    } else {
      return "huawei".equalsIgnoreCase(Build.BRAND) || "honor".equalsIgnoreCase(Build.BRAND);
    }
  }

  /**
   * 华为手机管家跳转
   * 操作步骤：应用启动管理 -> 关闭应用开关 -> 打开允许自启动
   */
  public static void goHuaweiSetting(Context context) {
    try {
      showActivity(context, "com.huawei.systemmanager", "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity");
    } catch (Exception e) {
      showActivity(context, "com.huawei.systemmanager", "com.huawei.systemmanager.optimize.bootstart.BootStartActivity");
    }
  }

  /**
   * 判断是否小米手机
   */
  public static boolean isXiaomi() {
    return "xiaomi".equalsIgnoreCase(Build.BRAND);
  }

  /**
   * 小米手机管家跳转
   * 操作步骤：授权管理 -> 自启动管理 -> 允许应用自启动
   */
  public static void goXiaomiSetting(Context context) {
    KLog.e("小米手机调整设置");
    showActivity(context, "com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity");
  }

  /**
   * 判断是否是OPPO手机
   *
   * @return 结果
   */
  public static boolean isOppo() {
    return "oppo".equalsIgnoreCase(Build.BRAND);
  }

  /**
   * OPPO手机管家跳转
   * 操作步骤：权限隐私 -> 自启动管理 -> 允许应用自启动
   */
  public static void goOppoSetting(Context context) {
    try {
      showActivity(context, "com.coloros.phonemanager");
    } catch (Exception e1) {
      try {
        showActivity(context, "com.oppo.safe");
      } catch (Exception e2) {
        try {
          showActivity(context, "com.coloros.oppoguardelf");
        } catch (Exception e3) {
          showActivity(context, "com.coloros.safecenter");
        }
      }
    }
  }

  /**
   * 判断是否是VIVO手机
   *
   * @return 结果
   */
  public static boolean isVivo() {
    return "vivo".equalsIgnoreCase(Build.BRAND);
  }

  /**
   * VIVO手机管家跳转
   * 操作步骤：权限管理 -> 自启动 -> 允许应用自启动
   */
  public static void goVivoSetting(Context context) {
    showActivity(context, "com.iqoo.secure");
  }

  /**
   * 判断是否是魅族手机
   *
   * @return 结果
   */
  public static boolean isMeizu() {
    return "meizu".equalsIgnoreCase(Build.BRAND);
  }

  /**
   * 魅族手机管家跳转
   * 操作步骤：权限管理 -> 后台管理 -> 点击应用 -> 允许后台运行
   */
  public static void goMeizuSetting(Context context) {
    showActivity(context, "com.meizu.safe");
  }

  /**
   * 判断是否是三星手机
   *
   * @return 结果
   */
  public static boolean isSamsung() {
    return "samsung".equalsIgnoreCase(Build.BRAND);
  }

  /**
   * 三星手机管家跳转
   * 操作步骤：自动运行应用程序 -> 打开应用开关 -> 电池管理 -> 未监视的应用程序 -> 添加应用
   */
  public static void goSamsungSetting(Context context) {
    try {
      showActivity(context, "com.samsung.android.sm_cn");
    } catch (Exception e) {
      showActivity(context, "com.samsung.android.sm");
    }
  }

  /**
   * 判断是否是乐视手机
   *
   * @return 结果
   */
  public static boolean isLeTv() {
    return "letv".equalsIgnoreCase(Build.BRAND);
  }

  /**
   * 乐视手机管家跳转
   * context 替换 "com.letv.android.letvsafe"
   * 操作步骤：自启动管理 -> 允许应用自启动
   */
  public static void goLetvSetting(Context context) {
    showActivity(context, "com.letv.android.letvsafe", "com.letv.android.letvsafe.AutobootManageActivity");
  }

  /**
   * 判断是否是锤子手机
   *
   * @return 结果
   */
  public static boolean isSmartisan() {
    return "smartisan".equalsIgnoreCase(Build.BRAND);
  }

  /**
   * 锤子手机管家跳转
   * 操作步骤：权限管理 -> 自启动权限管理 -> 点击应用 -> 允许被系统启动
   */
  public static void goSmartisanSetting(Context context) {
    showActivity(context, "com.smartisanos.security");
  }


}
