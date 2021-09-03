package io.jalorx.json.lookup;

import jakarta.inject.Named;

import io.jalorx.boot.Pair;
import io.jalorx.boot.json.MetaDataClient;
import io.jalorx.boot.model.Id;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.client.annotation.Client;

@Client(id = "demo", path = "/lookup")
@Named("Lookup.MetaDataClient")
@Requires(env = "json.remote")
public interface RemoteLookupMetaDataClient extends MetaDataClient<Id<Pair>,Pair> {

}
