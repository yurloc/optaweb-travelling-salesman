/*
 * Copyright 2012 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.optaweb.vehiclerouting.solver;

import java.text.NumberFormat;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.annotations.XStreamInclude;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.drools.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoftlong.HardSoftLongScore;
import org.optaplanner.persistence.xstream.api.score.buildin.hardsoftlong.HardSoftLongScoreXStreamConverter;
import org.optaweb.vehiclerouting.domain.Customer;
import org.optaweb.vehiclerouting.domain.Depot;
import org.optaweb.vehiclerouting.domain.Location;
import org.optaweb.vehiclerouting.domain.Vehicle;
import org.optaweb.vehiclerouting.domain.persistable.AbstractPersistable;
import org.optaweb.vehiclerouting.domain.timewindowed.TimeWindowedVehicleRoutingSolution;

@PlanningSolution
@XStreamAlias("VrpVehicleRoutingSolution")
@XStreamInclude({TimeWindowedVehicleRoutingSolution.class})
public class VehicleRoutingSolution extends AbstractPersistable {
    protected String name;
    private String distanceUnitOfMeasurement;
    private List<Location> locationList;
    private List<Depot> depotList;
    private List<Vehicle> vehicleList;
    private List<Customer> customerList;
    @XStreamConverter(HardSoftLongScoreXStreamConverter.class)
    private HardSoftLongScore score;

    public VehicleRoutingSolution() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDistanceUnitOfMeasurement() {
        return this.distanceUnitOfMeasurement;
    }

    public void setDistanceUnitOfMeasurement(String distanceUnitOfMeasurement) {
        this.distanceUnitOfMeasurement = distanceUnitOfMeasurement;
    }

    @ProblemFactCollectionProperty
    public List<Location> getLocationList() {
        return this.locationList;
    }

    public void setLocationList(List<Location> locationList) {
        this.locationList = locationList;
    }

    @ProblemFactCollectionProperty
    public List<Depot> getDepotList() {
        return this.depotList;
    }

    public void setDepotList(List<Depot> depotList) {
        this.depotList = depotList;
    }

    @PlanningEntityCollectionProperty
    @ValueRangeProvider(
            id = "vehicleRange"
    )
    public List<Vehicle> getVehicleList() {
        return this.vehicleList;
    }

    public void setVehicleList(List<Vehicle> vehicleList) {
        this.vehicleList = vehicleList;
    }

    @PlanningEntityCollectionProperty
    @ValueRangeProvider(
            id = "customerRange"
    )
    public List<Customer> getCustomerList() {
        return this.customerList;
    }

    public void setCustomerList(List<Customer> customerList) {
        this.customerList = customerList;
    }

    @PlanningScore
    public HardSoftLongScore getScore() {
        return this.score;
    }

    public void setScore(HardSoftLongScore score) {
        this.score = score;
    }

    // ************************************************************************
    // Complex methods
    // ************************************************************************

    public String getDistanceString(NumberFormat numberFormat) {
        if (score == null) {
            return null;
        }
        long distance = -score.getSoftScore();
        if (distanceUnitOfMeasurement == null) {
            return numberFormat.format(((double) distance) / 1000.0);
        }
        switch (distanceUnitOfMeasurement) {
            case "sec":  // TODO why are the values 1000 larger?
                long hours = distance / 3600000L;
                long minutes = distance % 3600000L / 60000L;
                long seconds = distance % 60000L / 1000L;
                long milliseconds = distance % 1000L;
                return hours + "h " + minutes + "m " + seconds + "s " + milliseconds + "ms";
            case "km": { // TODO why are the values 1000 larger?
                long km = distance / 1000L;
                long meter = distance % 1000L;
                return km + "km " + meter + "m";
            }
            case "meter": {
                long km = distance / 1000L;
                long meter = distance % 1000L;
                return km + "km " + meter + "m";
            }
            default:
                return numberFormat.format(((double) distance) / 1000.0) + " " + distanceUnitOfMeasurement;
        }
    }
}
