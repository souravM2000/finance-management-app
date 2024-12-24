package com.kshirsa.userservice.exernalservice;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckMailResponse {
    private boolean valid;
    private boolean block;
    private boolean disposable;
    private boolean emailForwarder;
    private String domain;
    private String text;
    private String reason;
    private String mxHost;
    private String possibleTypo;
    private String mxInfo;
    private String mxIp;
}
