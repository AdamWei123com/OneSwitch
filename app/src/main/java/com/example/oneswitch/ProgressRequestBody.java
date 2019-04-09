package com.example.oneswitch;
import okio.*;
import okhttp3.*;
import java.io.*;

public class ProgressRequestBody extends RequestBody
{
	MediaType MediaType;
    protected httpContextListener listener;
	protected File file;
	
	public ProgressRequestBody(MediaType MediaTye,File file, httpContextListener listener) {
		this.MediaType=MediaTye;
        this.listener = listener;
		this.file = file;
    }
	
	@Override
	public MediaType contentType()
	{
		// TODO: Implement this method
		return MediaType;
	}
	@Override public long contentLength() {
		return file.length();
	}
	
	@Override
	public void writeTo(BufferedSink p1) throws IOException
	{
		// TODO: Implement this method
		Source source;
		source=Okio.source(file);
		source = Okio.source(file);
		//sink.writeAll(source);
		Buffer buf = new Buffer();
		Long remaining = contentLength();
		for (long readCount; (readCount = source.read(buf, 2048)) != -1; ) {
			p1.write(buf, readCount);
			listener.update(contentLength(), remaining -= readCount, remaining == 0);
			
	}
	}
	}
