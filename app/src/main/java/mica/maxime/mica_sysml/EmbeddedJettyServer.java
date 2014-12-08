package mica.maxime.mica_sysml;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import mica.maxime.mica_sysml.servlet.Documents;

public class EmbeddedJettyServer extends Service implements Const{
    private Server server;
    private Executor serverExecutor;

    @Override
    public void onCreate() {
        super.onCreate();
        serverExecutor = Executors.newSingleThreadExecutor();
        server = new Server(8080);

        ServletContextHandler servletContextHandler = new ServletContextHandler();

        servletContextHandler.setContextPath("/");

        server.setHandler(servletContextHandler);

        servletContextHandler.addServlet(new ServletHolder(new Documents(this)), "/documents/*");
        serverExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    server.start();
                } catch (Exception e) {
                    Log.e(TAG, "starting server", e);
                }
            }
        });
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, " binding service : " + getClass().getSimpleName());
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            server.stop();
        } catch (Exception e) {
            Log.e(TAG, "stopping server", e);
        }
    }
}
