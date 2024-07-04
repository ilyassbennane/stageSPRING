package com.ramadan.api.exceptions;

import java.io.Serializable;


public class ApiError implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4130929122515363388L;
	
	private ErrorCode code;
    private String description;

    public ApiError(ErrorCode code, String description, String uuid) {
		super();
		this.code = code;
		this.description = description;
	}

    public ApiError(ErrorCode code, String description) {
        this.code = code;
        this.description = description;
    }

    public ErrorCode getCode() {
        return code;
    }

    public void setCode(ErrorCode code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
