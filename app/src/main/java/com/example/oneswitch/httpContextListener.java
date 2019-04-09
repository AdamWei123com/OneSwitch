package com.example.oneswitch;

interface httpContextListener {
    void update(long bytesRead, long contentLength, boolean done);
	void Download (long bytesRead, long contentLength, boolean done)
}
