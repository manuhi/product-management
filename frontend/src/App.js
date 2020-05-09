import React from 'react';
import './App.css';
import "bootstrap/dist/css/bootstrap.min.css";
import { BrowserRouter as Router, Switch, Route, Link } from "react-router-dom";

import AddProduct from "./components/product/add-product";
import Product from "./components/product/product";
import ProductList from "./components/product/product-list";

import AddCategory from "./components/category/add-category";
import Category from "./components/category/category";
import CategoryList from "./components/category/category-list";

class App extends React.Component {

  render() {
    return (
    <Router>
      <div>
        
        <nav className="navbar navbar-expand navbar-dark bg-dark">
          <a href="/product" className="navbar-brand">
            Product Management
          </a>
          <div className="navbar-nav mr-auto">
            <li className="nav-item">
              <Link to={"/product"} className="nav-link">
                Product List
              </Link>
            </li>
            <li className="nav-item border-md-right">
              <Link to={"/addProduct"} className="nav-link">
                Add Product
              </Link>
            </li>
            <li className="nav-item">
              <Link to={"/category"} className="nav-link">
                Category List
              </Link>
            </li>
            <li className="nav-item">
              <Link to={"/addCategory"} className="nav-link">
                Add Category
              </Link>
            </li>
          </div>
        </nav>

        <div className="container mt-3">
          <Switch>
            <Route exact path={["/", "/product"]} component={ProductList} />
            <Route exact path="/addProduct" component={AddProduct} />
            <Route path="/product/:id" component={Product} />
            <Route exact path="/category" component={CategoryList} />
            <Route exact path="/addCategory" component={AddCategory} />
            <Route path="/category/:id" component={Category} />
          </Switch>
        </div>

      </div>
    </Router>
    );
  }

}

export default App;
