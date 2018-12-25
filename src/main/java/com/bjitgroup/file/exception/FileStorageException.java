package com.bjitgroup.file.exception;

public class FileStorageException extends RuntimeException {
	
	private static final long serialVersionUID = -2652375521870539750L;

	public FileStorageException(String message) {
		super(message);
	}

	public FileStorageException(String message, Throwable cause) {
		super(message, cause);
	}
}