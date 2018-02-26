package com.claro.moose.util.authentication;

import com.claro.moose.models.Role;
import com.claro.moose.ws.authentication.AutenticaToken;
import com.claro.moose.ws.authentication.AutenticaTokenResponse;
import com.claro.moose.ws.authentication.BuscaAccesos;
import com.claro.moose.ws.authentication.BuscaAccesosResponse;
import com.claro.moose.ws.authentication.BuscaRol;
import com.claro.moose.ws.authentication.BuscaRolDescripcion;
import com.claro.moose.ws.authentication.BuscaRolDescripcionResponse;
import com.claro.moose.ws.authentication.BuscaRolResponse;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

public class PAWClient extends WebServiceGatewaySupport {

    private static final Logger log = LoggerFactory.getLogger(PAWClient.class);

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${paw.soap.namespace}")
    private String namespace;

	public boolean isValidToken(String username, long token) {

		AutenticaToken request = new AutenticaToken();
        request.setVUsr(username);
        request.setVToken(token);
        request.setVApp(applicationName);

		log.info("Authenticating token: " + token);

		AutenticaTokenResponse response = (AutenticaTokenResponse) getWebServiceTemplate()
				.marshalSendAndReceive(request,
						new SoapActionCallback(namespace + "AutenticaToken"));

		return response.isAutenticaTokenResult();
    }

	private int getRoleIdByUsername(String username) {

        BuscaRol request = new BuscaRol();
        request.setVApp(applicationName);
        request.setVUsu(username);

		BuscaRolResponse response = (BuscaRolResponse) getWebServiceTemplate()
				.marshalSendAndReceive(request,
						new SoapActionCallback(namespace + "BuscaRol"));

		return response.getBuscaRolResult();
    }
    

	private String getRoleDescriptionByUsername(String username) {

        BuscaRolDescripcion request = new BuscaRolDescripcion();
        request.setVApp(applicationName);
        request.setVUsu(username);

		BuscaRolDescripcionResponse response = (BuscaRolDescripcionResponse) getWebServiceTemplate()
				.marshalSendAndReceive(request,
						new SoapActionCallback(namespace + "BuscaRolDescripcion"));

		return response.getBuscaRolDescripcionResult();
    }
    
    private List<String> getPermissionsByRoleId(int roleId) {
        BuscaAccesos request = new BuscaAccesos();
        request.setVApp(applicationName);
        request.setVRol(roleId);

		BuscaAccesosResponse response = (BuscaAccesosResponse) getWebServiceTemplate()
				.marshalSendAndReceive(request,
						new SoapActionCallback(namespace + "BuscaAccesos"));

		return response.getBuscaAccesosResult().getString();
    }

    public Role getRoleByUsername(String username) {
        int roleId = getRoleIdByUsername(username);
        String roleDesc = getRoleDescriptionByUsername(username);
        List<String> permissions = getPermissionsByRoleId(roleId);

        return new Role(roleId, roleDesc, permissions);
    }
}