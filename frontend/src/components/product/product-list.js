import React from 'react';
import ProductService from '../../http/product-service'
import { Link } from "react-router-dom";

class ProductList extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            products: []
        }
    }

    componentDidMount() {
        
        this.products = ProductService.getAllProducts()
        
        .then(response => {
            this.setState({
                products: response.data
            });
        })
        
        .catch(e => {
            console.log(e);
        });
    }

    render() {
        return (
            <div>
                <h1>Products</h1>
                <br/>
                <table class="table">
                    <thead>
                        <tr>
                            <th scope="col">ID</th>
                            <th scope="col">Name</th>
                            <th scope="col">Description</th>
                            <th scope="col">Price</th>
                            <th scope="col">Category</th>
                        </tr>
                    </thead>
                    <tbody>
                        {this.state.products.map(product => (
                            <tr>
                                <td>
                                <Link
                                    to={"/product/" + product.id}
                                    className="badge badge-info"
                                >
                                    {product.id}
                                </Link>
                                </td>
                                <td>{product.name}</td>
                                <td>{product.description}</td>
                                <td>{product.price}</td>
                                <td>{product.category.name}</td>
                            </tr>
                        ))}
    
                    </tbody>
                </table>
            </div>
        );
    }

}

export default ProductList;