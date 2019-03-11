/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.optaweb.vehiclerouting.plugin.websocket;

import java.util.Collection;

/**
 * Routing plan representation convenient for marshalling.
 */
public class PortableRoutingPlan {

    private final String distance;
    private final Collection<PortableRoute> routes;

    public PortableRoutingPlan(String distance, Collection<PortableRoute> routes) {
        this.distance = distance;
        this.routes = routes;
    }

    public String getDistance() {
        return distance;
    }

    public Collection<PortableRoute> getRoutes() {
        return routes;
    }
}