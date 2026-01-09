package framework.tests.api;

import framework.core.model.request.LaunchInfoRequest;

import java.util.Collections;

public class LaunchInfoRequestBuilder {

    public static LaunchInfoRequest build(int launchId) {
        LaunchInfoRequest r = new LaunchInfoRequest();
        r.setIds(Collections.singletonList(launchId));

        LaunchInfoRequest.Description desc = new LaunchInfoRequest.Description();
        desc.setComment("DDDDD");
        desc.setAction("CREATE");
        r.setDescription(desc);

        LaunchInfoRequest.KeyValue from = new LaunchInfoRequest.KeyValue();
        from.setKey("DDDD");
        from.setValue("DDDD");

        LaunchInfoRequest.KeyValue to = new LaunchInfoRequest.KeyValue();
        to.setKey("DDDD");
        to.setValue("DDDD");

        LaunchInfoRequest.AttributeChange attr = new LaunchInfoRequest.AttributeChange();
        attr.setFrom(from);
        attr.setTo(to);
        attr.setAction("CREATE");

        r.setAttributes(Collections.singletonList(attr));

        return r;
    }
}
