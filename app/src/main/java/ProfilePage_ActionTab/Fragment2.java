package ProfilePage_ActionTab;
/**
 * Created by Suganprabu on 07-02-2015.
 */
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.exebit.exebit2k15.Main_Activity;
import com.example.exebit.exebit2k15.R;

public class Fragment2 extends Fragment {

    WebView webView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout resource that'll be returned

        if(savedInstanceState==null) {
            Log.i("saved instance state", "is null");
        }
        else { Log.i("saved instance state", "is not null");

        }

        View rootView = inflater.inflate(R.layout.fragment_dash_board, container, false);

        webView = (WebView) rootView.findViewById(R.id.webview1);

        WebSettings webSettings = webView.getSettings();
        webView.setVerticalScrollBarEnabled(false);

//            webView.loadUrl("http://exebit.in/enter.php");
          /*  byte[] post = EncodingUtils.getBytes("name=" + Main_Activity.userName + "&password=" + Main_Activity.userPassword, "base64");
            webView.postUrl("http://exebit.in/backend/_login.php", post);
*/
            /*webView.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    WebView.HitTestResult hr = ((WebView) v).getHitTestResult();

                    Log.i("touched", "getExtra = " + hr.getExtra() + "\t\t Type=" + hr.getType());
                    if(hr.getExtra()==null && hr.getType()==0) {
                        Toast.makeText(rootView.getContext(),"time to hide the webview",Toast.LENGTH_SHORT).show();
                    }
                    return false;
                }
            });*/


            /*Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                    nameValuePairs.add(new BasicNameValuePair("name", "arvindsuresh2009@gmail.com"));
                    nameValuePairs.add(new BasicNameValuePair("password", "helloworld"));

                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost("http://exebit.in/backend/_login.php");
                    try {
                        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    HttpResponse response = null;
                    try {
                        response = httpclient.execute(httppost);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String data = null;
                    try {
                        data = new BasicResponseHandler().handleResponse(response);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    webView.loadData(data, "text/html", "utf-8");
                }
            });

            t1.start();
*/

        webView.loadDataWithBaseURL("http://exebit.in","\n" +
                "\n" +
                "<html>\n" +
                "<head>\n" +
                "    <title>Login / Register</title>\n" +
                "\n" +
                "    <!-- Style sheets -->\n" +
                "    <link rel=\"stylesheet\" media=\"(max-width: 1000px)\" href=\"css/enter-mobile.css\" />\n" +
                "\n" +
                "    <link href=\"css/enter.css\" media=\"(min-width: 1000px)\" rel=\"stylesheet\" />\n" +
                "\n" +
                "    <meta name=\"viewport\" content=\"initial-scale=1.0, width=device-width, height=device-height, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no\" />\n" +
                "\n" +
                "\n" +
                "    <!-- Font files -->\n" +
                "    \n" +
                "<!-- Google Roboto -->\n" +
                "<link href='http://fonts.googleapis.com/css?family=Roboto:400,100,100italic,300,300italic,400italic,500,500italic,700,700italic,900,900italic' rel='stylesheet' type='text/css'>\n" +
                "\n" +
                "<!-- Google Roboto Condensed -->\n" +
                "<link href='http://fonts.googleapis.com/css?family=Roboto+Condensed:300italic,400italic,700italic,400,300,700' rel='stylesheet' type='text/css'>\n" +
                "\n" +
                "<!-- Lato -->\n" +
                "<link href='http://fonts.googleapis.com/css?family=Lato:100,300,400,700,900,100italic,300italic,400italic,700italic,900italic' rel='stylesheet' type='text/css'></head>\n" +
                "\n" +
                "<body>\n" +
                "<header>\n" +
                "    \n" +
                "<div id=\"global-header\">\n" +
                "    <div id=\"global-header-content\">\n" +
                "        <div id=\"global-header-left\">\n" +
                "            <span id=\"title-span\"><a href=\"http://www.exebit.in\">Exebit 2015</a></span>\n" +
                "        </div>\n" +
                "\n" +
                "        <div id=\"global-header-right\">\n" +
                "\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</div>\n" +
                "\n" +
                "<style>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    @media (max-width: 1000px) {\n" +
                "        #global-header {\n" +
                "            background-color: #FFFFFF;\n" +
                "            width: 100%;\n" +
                "            padding: 20px 0 20px 0;\n" +
                "            border-bottom: 1px solid #90CAF9;\n" +
                "            overflow: auto;\n" +
                "        }\n" +
                "\n" +
                "        #global-header-content {\n" +
                "            width: 95%;\n" +
                "            margin: 0 auto;\n" +
                "            overflow: auto;\n" +
                "        }\n" +
                "\n" +
                "        #title-span {\n" +
                "            cursor: pointer;\n" +
                "            font-family: 'Roboto';\n" +
                "            font-weight: 300;\n" +
                "            letter-spacing: 1px;\n" +
                "            text-transform: uppercase;\n" +
                "            color: #666;\n" +
                "            font-size: 1.5em;\n" +
                "            transition: 400ms color;\n" +
                "        }\n" +
                "\n" +
                "        #global-header-left {\n" +
                "            width: 100%;\n" +
                "            text-align: center;\n" +
                "            margin-bottom: 10px;\n" +
                "        }\n" +
                "\n" +
                "        #global-header-right {\n" +
                "            width: 100%;\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "\n" +
                "        .global-menu {\n" +
                "            font-family: 'Roboto';\n" +
                "            font-weight: 400;\n" +
                "            text-transform: uppercase;\n" +
                "            color: #AAAAAA;\n" +
                "            margin-top: 5px;\n" +
                "            margin-left: 20px;\n" +
                "            display: inline-block;\n" +
                "            font-size: 0.8em;\n" +
                "            cursor: pointer;\n" +
                "            padding: 5px;\n" +
                "            transition: 400ms color;\n" +
                "        }\n" +
                "\n" +
                "        .float-out {\n" +
                "            background-color: #2196F3;\n" +
                "            color: white;\n" +
                "            border-radius: 3px;\n" +
                "        }\n" +
                "\n" +
                "        .float-out:hover {\n" +
                "            background-color: white;\n" +
                "            border: 1px solid #2196F3;\n" +
                "        }\n" +
                "\n" +
                "        .global-menu:first-child {\n" +
                "            margin-left: 0px !important;\n" +
                "        }\n" +
                "\n" +
                "        .global-menu:hover {\n" +
                "            color: #2196F3;\n" +
                "        }\n" +
                "\n" +
                "        #title-span:hover {\n" +
                "            color: #222;\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "    @media (min-width: 1000px) {\n" +
                "        #global-header {\n" +
                "            background-color: #FFFFFF;\n" +
                "            width: 100%;\n" +
                "            padding: 20px 0 20px 0;\n" +
                "            border-bottom: 1px solid #90CAF9;\n" +
                "            overflow: auto;\n" +
                "        }\n" +
                "\n" +
                "        #global-header-content {\n" +
                "            width: 1000px;\n" +
                "            margin: 0 auto;\n" +
                "            overflow: auto;\n" +
                "        }\n" +
                "\n" +
                "        #title-span {\n" +
                "            cursor: pointer;\n" +
                "            font-family: 'Roboto';\n" +
                "            font-weight: 300;\n" +
                "            letter-spacing: 1px;\n" +
                "            text-transform: uppercase;\n" +
                "            color: #666;\n" +
                "            font-size: 1.4em;\n" +
                "            transition: 400ms color;\n" +
                "        }\n" +
                "\n" +
                "        #global-header-left {\n" +
                "            float: left;\n" +
                "        }\n" +
                "\n" +
                "        #global-header-right {\n" +
                "            float: right;\n" +
                "        }\n" +
                "\n" +
                "        .global-menu {\n" +
                "            font-family: 'Roboto';\n" +
                "            font-weight: 400;\n" +
                "            text-transform: uppercase;\n" +
                "            color: #AAAAAA;\n" +
                "            margin-left: 20px;\n" +
                "            display: inline-block;\n" +
                "            font-size: 0.9em;\n" +
                "            cursor: pointer;\n" +
                "            padding: 5px;\n" +
                "            transition: 400ms color;\n" +
                "            border: 1px solid transparent;\n" +
                "        }\n" +
                "        \n" +
                "        .global-menu a {\n" +
                "            text-decoration: none;\n" +
                "        }\n" +
                "\n" +
                "        .float-out {\n" +
                "            background-color: #2196F3;\n" +
                "            color: white;\n" +
                "            border-radius: 3px;\n" +
                "        }\n" +
                "\n" +
                "        .red {\n" +
                "            background-color: #D0021B !important;\n" +
                "        }\n" +
                "\n" +
                "        .red:hover {\n" +
                "            color: #D0021B !important;\n" +
                "            border: 1px solid #D0021B !important;\n" +
                "            background-color: white !important;\n" +
                "        }\n" +
                "\n" +
                "        .float-out:hover {\n" +
                "            background-color: white;\n" +
                "            border: 1px solid #2196F3;\n" +
                "        }\n" +
                "\n" +
                "        .global-menu:first-child {\n" +
                "            margin-left: 0px !important;\n" +
                "        }\n" +
                "\n" +
                "        .global-menu:hover {\n" +
                "            color: #2196F3;\n" +
                "        }\n" +
                "\n" +
                "        #title-span:hover {\n" +
                "            color: #222;\n" +
                "        }\n" +
                "\n" +
                "\n" +
                "    }\n" +
                "\n" +
                "</style></header>\n" +
                "\n" +
                "<div id=\"container\">\n" +
                "    <div id=\"login-pane\">\n" +
                "        <div class=\"login-pane-header\">\n" +
                "            <span>LOGIN</span>\n" +
                "        </div>\n" +
                "\n" +
                "        <form action=\"backend/_login.php\" method=\"post\">\n" +
                "            <div class=\"box\">\n" +
                "                <div class=\"box-hint\">\n" +
                "                    <span>Enter your email</span>\n" +
                "                </div>\n" +
                "                <input class=\"blueField\" type=\"text\" name=\"email\" value=\""+ Main_Activity.userName+"\" />\n" +
                "            </div>\n" +
                "\n" +
                "            <div class=\"box\">\n" +
                "                <div class=\"box-hint\">\n" +
                "                    <span>Enter your password</span>\n" +
                "                </div>\n" +
                "                <input class=\"blueField\" type=\"password\" name=\"password\"value=\""+Main_Activity.userPassword+"\" />\n" +
                "            </div>\n" +
                "\n" +
                "            <div class=\"box\">\n" +
                "                <input type=\"submit\" value=\"LOGIN\" class=\"blueButton\" />\n" +
                "            </div>\n" +
                "        </form>\n" +
                "\n" +
                "        <span id=\"forgot-password\"><a href=\"forgot-password.php\">Forgot Password?</a></span>\n" +
                "\n" +
                "    </div>\n" +
                "\n" +
                "<style>\n" +
                "    @media (max-width: 1000px) {\n" +
                "        #global-footer {\n" +
                "            width: 100%;\n" +
                "            background-color: #222222;\n" +
                "            padding: 20px 0 10px 0;\n" +
                "        }\n" +
                "\n" +
                "        #global-footer-content {\n" +
                "            width: 95%;\n" +
                "            margin: 0 auto;\n" +
                "            overflow: auto;\n" +
                "        }\n" +
                "\n" +
                "        .global-footer-pane {\n" +
                "            width: 95%;\n" +
                "        }\n" +
                "\n" +
                "        .global-footer-pane-header {\n" +
                "            margin-bottom: 10px;\n" +
                "            padding-bottom: 5px;\n" +
                "        }\n" +
                "\n" +
                "        .global-footer-pane-header span {\n" +
                "            color: white;\n" +
                "            font-family: 'Roboto';\n" +
                "            font-weight: 200;\n" +
                "            letter-spacing: 1px;\n" +
                "            color: #AAAAAA;\n" +
                "            font-size: 1.2em;\n" +
                "        }\n" +
                "\n" +
                "        .global-footer-pane-items span {\n" +
                "            color: white;\n" +
                "            font-family: 'Roboto';\n" +
                "            font-weight: 300;\n" +
                "            color: #666666;\n" +
                "            display: inline-block;\n" +
                "            margin-bottom: 10px;\n" +
                "            cursor: pointer;\n" +
                "            transition: 400ms color;\n" +
                "            display: none;\n" +
                "        }\n" +
                "\n" +
                "        .global-footer-pane-items span:hover {\n" +
                "            color: #888888;\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "    @media (min-width: 1000px) {\n" +
                "        #global-footer {\n" +
                "            width: 100%;\n" +
                "            height: 200px;\n" +
                "            background-color: #222222;\n" +
                "            padding: 20px 0 20px 0;\n" +
                "        }\n" +
                "\n" +
                "        #global-footer-content {\n" +
                "            width: 1000px;\n" +
                "            margin: 0 auto;\n" +
                "            overflow: auto;\n" +
                "        }\n" +
                "\n" +
                "        .global-footer-pane {\n" +
                "            float: left;\n" +
                "            width: 200px;\n" +
                "            margin-right: 50px;\n" +
                "        }\n" +
                "\n" +
                "        .global-footer-pane-header {\n" +
                "            margin-bottom: 10px;\n" +
                "            width: 100%;\n" +
                "            padding-bottom: 5px;\n" +
                "            border-bottom: 1px solid #333333;\n" +
                "        }\n" +
                "\n" +
                "        .global-footer-pane-header span {\n" +
                "            color: white;\n" +
                "            font-family: 'Roboto';\n" +
                "            font-weight: 200;\n" +
                "            letter-spacing: 1px;\n" +
                "            color: #AAAAAA;\n" +
                "            font-size: 1.2em;\n" +
                "        }\n" +
                "\n" +
                "        .global-footer-pane-items span {\n" +
                "            color: white;\n" +
                "            font-family: 'Roboto';\n" +
                "            font-weight: 300;\n" +
                "            color: #666666;\n" +
                "            display: inline-block;\n" +
                "            margin-bottom: 10px;\n" +
                "            cursor: pointer;\n" +
                "            transition: 400ms color;\n" +
                "        }\n" +
                "\n" +
                "        .global-footer-pane-items span:hover {\n" +
                "            color: #888888;\n" +
                "        }\n" +
                "    }\n" +
                "</style></footer>\n" +
                "</body>\n" +
                "</html>","text/html","UTF-8",null);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }
        });

        String script = "<script>document.getElementsByName('email')[0].value='" + Main_Activity.userName + "';document.getElementsByName('password')[0].value='" + Main_Activity.userPassword +"';</script>";


        // Get the arguments that was supplied when
        // the fragment was instantiated in the
        // CustomPagerAdapter
        return rootView;

    }}
