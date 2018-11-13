/*
 * Copyright 2018 Red Hat, Inc. and/or its affiliates.
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

import React, { Component } from 'react';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import 'tachyons/css/tachyons.css';
import { tspOperations } from '../store/tsp/index';
import TSPProblem from '../components/TSPProblem';

const mapStateToProps = state => ({
  tsp: state.tsp,
});

const mapDispatchToProps = dispatch => ({
  loadHandler() {
    dispatch(tspOperations.loadDemo());
  },
  addHandler({ latlng }) {
    dispatch(tspOperations.addLocation(latlng));
  },
  removeHandler(id) {
    dispatch(tspOperations.deleteLocation(id));
  },
});

class App extends Component {
  constructor() {
    super();
    this.onClickRemove = this.onClickRemove.bind(this);
  }

  onClickRemove(id) {
    const {
      tsp: { domicileId, route },
      removeHandler,
    } = this.props;
    if (id !== domicileId || route.length === 1) {
      removeHandler(id);
    }
  }

  render() {
    return (
      <div>
        <TSPProblem {...this.props} />
      </div>
    );
  }
}

App.propTypes = {
  tsp: PropTypes.shape({
    route: PropTypes.array.isRequired,
    domicileId: PropTypes.number.isRequired,
    distance: PropTypes.string.isRequired,
  }),
  loadHandler: PropTypes.func.isRequired,
  addHandler: PropTypes.func.isRequired,
  removeHandler: PropTypes.func.isRequired,
};

App.defaultProps = {
  tsp: {
    route: [],
    domicileId: -1,
    distance: '0',
  },
};

export default connect(
  mapStateToProps,
  mapDispatchToProps,
)(App);
