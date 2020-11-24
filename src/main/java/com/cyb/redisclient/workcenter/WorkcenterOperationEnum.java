package com.cyb.redisclient.workcenter;

import com.cyb.redisclient.common.WebConstant;

public enum WorkcenterOperationEnum implements WebConstant {
	//{001,workcenter 002,admin}
	
	;
	
	String operationCode;
	String operationEn;
	String operationCh;
	
	WorkcenterOperationEnum(String code, String en, String ch) {
		operationCode = code;
		operationEn = en;
		operationCh = ch;
	}
	
	public String getOperationCode() {
		return operationCode;
	}
	public String getOperationEn() {
		return operationEn;
	}

	public String getOperationCh() {
		return operationCh;
	}
	
}
