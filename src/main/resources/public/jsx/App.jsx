import React from 'react'
import { render } from 'react-dom'
import { Router, Route, IndexRoute, browserHistory } from 'react-router'
import { I18nextProvider } from 'react-i18next'
import Cookies from 'js-cookie'

// load jquery and foundation in the window scope
import 'script!jquery'
import 'script!what-input'
import 'script!foundation-sites'

import i18n from './util/i18n'
import Home from './Home'

class App extends React.Component {
    static propTypes = {
        children: React.PropTypes.element.isRequired
    }
    static childContextTypes = {
        pathname: React.PropTypes.string,
        search: React.PropTypes.string
    }
    constructor(props) {
        super(props)
    }
    getChildContext() {
        return {
            pathname: this.props.location.pathname,
            search: this.props.location.query.search
        };
    }
    componentDidMount() {
        $(document).foundation()
    }
    render() {
        return (
            <div>
                {this.props.children}
            </div>
        )
    }
}

render((
    <I18nextProvider i18n={i18n}>
        <Router history={browserHistory}>
            <Route path="/" component={App}>
                <IndexRoute component={Home}/>
            </Route>
        </Router>
    </I18nextProvider>
), document.getElementById('app'))
