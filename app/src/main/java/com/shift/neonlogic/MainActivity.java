package com.shift.neonlogic;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.widget.Toast;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

public class MainActivity extends Activity {
 private WebView web;
 private String pendingExport;
 private static final int SAVE = 201, OPEN = 202;
 @Override public void onCreate(Bundle state) { super.onCreate(state);
  getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
  web = new WebView(this); web.setBackgroundColor(0xff080e1b); setContentView(web);
  WebSettings s=web.getSettings(); s.setJavaScriptEnabled(true); s.setDomStorageEnabled(true); s.setAllowFileAccess(true);
  s.setAllowFileAccessFromFileURLs(false); s.setAllowUniversalAccessFromFileURLs(false);
  web.setWebChromeClient(new WebChromeClient());
  web.setWebViewClient(new WebViewClient(){
   @Override public boolean shouldOverrideUrlLoading(WebView v, String url){return !url.startsWith("file:///android_asset/");}
   @Override public boolean shouldOverrideUrlLoading(WebView v, WebResourceRequest request){return !request.getUrl().toString().startsWith("file:///android_asset/");}
  });
  web.addJavascriptInterface(new Bridge(),"ShiftAndroid");
  web.loadUrl("file:///android_asset/index.html");
 }
 public class Bridge {
  @JavascriptInterface public void exportBackup(String json) {
   if(json==null || json.length()>300000)return;
   runOnUiThread(()->{pendingExport=json;Intent i=new Intent(Intent.ACTION_CREATE_DOCUMENT);i.addCategory(Intent.CATEGORY_OPENABLE);i.setType("application/json");i.putExtra(Intent.EXTRA_TITLE,"shift-salvataggio.json");startActivityForResult(i,SAVE);});
  }
  @JavascriptInterface public void importBackup() {
   runOnUiThread(()->{Intent i=new Intent(Intent.ACTION_OPEN_DOCUMENT);i.addCategory(Intent.CATEGORY_OPENABLE);i.setType("application/json");startActivityForResult(i,OPEN);});
  }
 }
 @Override protected void onActivityResult(int req,int result,Intent data){super.onActivityResult(req,result,data);
  if(result!=RESULT_OK||data==null||data.getData()==null)return;
  Uri uri=data.getData();
  try {
   if(req==SAVE && pendingExport!=null){try(OutputStream out=getContentResolver().openOutputStream(uri)){out.write(pendingExport.getBytes(StandardCharsets.UTF_8));}pendingExport=null;Toast.makeText(this,"Backup salvato",Toast.LENGTH_SHORT).show();}
   else if(req==OPEN){byte[] bytes;try(InputStream in=getContentResolver().openInputStream(uri)){ByteArrayOutputStream out=new ByteArrayOutputStream();byte[] buf=new byte[8192];int n;while((n=in.read(buf))!=-1){out.write(buf,0,n);if(out.size()>300000)throw new IllegalArgumentException("File troppo grande");}bytes=out.toByteArray();}String json=new String(bytes,StandardCharsets.UTF_8);String encoded=android.util.Base64.encodeToString(json.getBytes(StandardCharsets.UTF_8),android.util.Base64.NO_WRAP);
    web.evaluateJavascript("window.__shiftNativeRestore(decodeURIComponent(escape(atob('"+encoded+"'))))",null);
   }
  }catch(Exception ex){Toast.makeText(this,"Operazione non riuscita",Toast.LENGTH_LONG).show();}
 }
 @Override public void onBackPressed(){web.evaluateJavascript("window.__shiftAndroidBack && window.__shiftAndroidBack()",null);}
 @Override protected void onDestroy(){web.destroy();super.onDestroy();}
}
