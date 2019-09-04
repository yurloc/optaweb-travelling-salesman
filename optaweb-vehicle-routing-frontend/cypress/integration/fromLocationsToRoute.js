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

describe('Locations can be added and route is computed', () => {

  /**
   * Adds a location by searching for a city of a given name.
   * @param {city name} name
   */
  const addCity = (name) => {
    cy.get('[data-cy=demo-location-text-input]').type(name);
    cy.get('[data-cy=demo-location-item-button-1]').click();
  };

  /**
   * Clears locations by clicking on a 'Clear' button.
   */
  const clearLocations = () => {
    // Add one city to make sure there is a location in the list and the clear button shows up
    addCity('Brussels');
    cy.get('[data-cy=demo-clear-button]').click({ force: true });
  }

  /**
   * Waits for a websocket connection to be established.
   */
  const waitForWebSocketToConnect = () => {
    cy.server();
    cy.route('/vrp-websocket/*').as('vrp');
    cy.visit('/demo');
    cy.wait('@vrp');
  };

  before(() => {
    waitForWebSocketToConnect();
    clearLocations();
  });

  it('Locations added via clicking on a map are added to a route', function () {
    const cities = ['Waterloo', 'Gent'];

    cities.forEach((city) => {
      addCity(city);
    });

    cy.get('[data-cy=demo-add-vehicle]').click();
    cy.visit('/route');
    cy.get('[data-cy=location-list]').find('li').should((list) => {
      cities.forEach((city) => expect(list).to.contain(city));
    });
  });
});
