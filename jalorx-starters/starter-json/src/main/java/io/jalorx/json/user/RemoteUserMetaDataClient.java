package io.jalorx.json.user;

import jakarta.inject.Named;

import io.jalorx.boot.json.MetaDataClient;
import io.jalorx.boot.model.Id;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.client.annotation.Client;

@Client(id = "demo", path = "/security/user")
@Named("User.MetaDataClient")
@Requires(env = "json.remote")
public interface RemoteUserMetaDataClient extends MetaDataClient<Id,String> {

}
