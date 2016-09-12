package com.humama.common.httpclient;

import org.apache.http.conn.HttpClientConnectionManager;

/**
 * @描述: .
 * @作者: ZHaoWeiNan .
 * @创建时间: 2016/9/10 .
 * @版本: 1.0 .
 */
public class IdleConnectionEvictor extends Thread {

    private final HttpClientConnectionManager connMgr;

    private volatile boolean shutdown;

    public IdleConnectionEvictor(HttpClientConnectionManager connMgr) {
        this.connMgr = connMgr;
        this.start();//启动当前线程
    }

    @Override
    public void run() {
        try {
            while (!shutdown) {
                synchronized (this) {
                    wait(5000);
                    // 关闭失效的连接
                    connMgr.closeExpiredConnections();
                }
            }
        } catch (InterruptedException ex) {
            // 结束
        }
    }

    public void shutdown() {
        shutdown = true;
        synchronized (this) {
            notifyAll();
        }
    }
}
