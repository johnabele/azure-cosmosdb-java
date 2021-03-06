/*
 *
 * The MIT License (MIT)
 * Copyright (c) 2018 Microsoft Corporation
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.microsoft.azure.cosmosdb.rx.internal;

import java.net.URL;

import com.microsoft.azure.cosmosdb.ConsistencyLevel;
import com.microsoft.azure.cosmosdb.PartitionKeyRange;
import com.microsoft.azure.cosmosdb.internal.ISessionToken;
import com.microsoft.azure.cosmosdb.internal.RequestChargeTracker;

public class DocumentServiceRequestContext {
    public volatile boolean forceAddressRefresh;
    public volatile RequestChargeTracker requestChargeTracker;
    public volatile String resolvedCollectionRid;
    public volatile ISessionToken sessionToken;
    public volatile long quorumSelectedLSN;
    public volatile long globalCommittedSelectedLSN;
    public volatile long globalStrongWriteResponse;
    public volatile ConsistencyLevel originalRequestConsistencyLevel;
    public volatile PartitionKeyRange resolvedPartitionKeyRange;
    public volatile Integer regionIndex;
    public volatile Boolean usePreferredLocations;
    public volatile Integer locationIndexToRoute;
    public volatile URL locationEndpointToRoute;

    /**
     * Sets routing directive for GlobalEndpointManager to resolve the request
     *  to endpoint based on location index.
     *
     * @param locationIndex Index of the location to which the request should be routed.
     * @param usePreferredLocations Use preferred locations to route request.
     */
    public void RouteToLocation(int locationIndex, boolean usePreferredLocations) {
        this.locationIndexToRoute = locationIndex;
        this.usePreferredLocations = usePreferredLocations;
        this.locationEndpointToRoute = null;
    }

    /**
     * Sets location-based routing directive for GlobalEndpointManager to resolve
     * the request to given locationEndpoint.
     *
     * @param locationEndpoint Location endpoint to which the request should be routed.
     */
    public void RouteToLocation(URL locationEndpoint) {
        this.locationEndpointToRoute = locationEndpoint;
        this.locationIndexToRoute = null;
        this.usePreferredLocations = null;
    }

    /**
     * Clears location-based routing directive
     */
    public void ClearRouteToLocation() {
        this.locationIndexToRoute = null;
        this.locationEndpointToRoute = null;
        this.usePreferredLocations = null;
    }
}

