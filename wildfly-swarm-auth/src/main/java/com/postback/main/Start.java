package com.postback.main;

public class Start {

    public static void main(String[] args) throws Exception {
        BackendContainer.newContainer()
                .start()
                .deploy(BackendDeployment.createDeployment());

    }

}
