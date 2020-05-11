import React from 'react';
import CategoryService from '../../http/category-service'
import CurrencyService from '../../http/currency-service'
import ProductService from '../../http/product-service'
import { Redirect } from 'react-router-dom';

class AddProduct extends React.Component {

    constructor(props) {
        super(props);
        this.onChangeName = this.onChangeName.bind(this);
        this.onChangeDescription = this.onChangeDescription.bind(this);
        this.onChangePrice = this.onChangePrice.bind(this);
        this.onChangePriceSymbol = this.onChangePriceSymbol.bind(this);
        this.onChangeCategory = this.onChangeCategory.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);

        this.state = {
            id: null,
            name: "",
            description: "", 
            price: "",
            priceSymbol: "",
            categoryId: "",
            submitted: false,
            categories: [],
            priceSymbols: []
        };
    }

    onChangeName(e) {
        this.setState({
            name: e.target.value
        });
    }

    onChangeDescription(e) {
        this.setState({
            description: e.target.value
        });
    }

    onChangePrice(e) {
        this.setState({
            price: e.target.value
        })
    }

    onChangePriceSymbol(e) {
        this.setState({
            priceSymbol: e.target.value
        })
    }

    onChangeCategory(e) {

        this.setState({
            categoryId: e.target.value
        })
    }

    componentDidMount() {
        this.categories = CategoryService.getAllCategories()
            .then(response => {
                this.setState({
                    categories: response.data
                });
                if (this.state.categories.length > 0) {
                    this.setState({
                        categoryId: response.data[0].id
                    })
                }
            })
            .catch(e => {
                console.log(e);
            });

        this.priceSymbols = CurrencyService.getAllAvailablePriceSymbols()
            .then(response => {
                this.setState({
                    priceSymbols: response.data
                })
                this.setState({
                    priceSymbol: "EUR"
                })
            })
            .catch(e => {
                console.log(e);
            });
    }

    handleSubmit(event) {

        event.preventDefault();

        var data = {
            name: this.state.name,
            description: this.state.description,
            priceInput: {
                symbolKey: this.state.priceSymbol,
                value: this.state.price
            },
            categoryId: this.state.categoryId
        };
        
        ProductService.saveProduct(data)
        
        .then(response => {
            this.setState({
                id: response.data.id,
                name: response.data.name,
                description: response.data.description,
                price: response.data.price,
                submitted: true
            })
        })
            
        .catch(e => {
            console.log(e);
        });

    }

    render() {
        return (
            <div className="submit-form">
                {this.state.submitted ? (
                    <Redirect to="/product"/>
                ) : (
                <div>
                    <h1>Add a Product</h1>
                    <br/>
                    <form onSubmit={this.handleSubmit} >
                        <div className="form-group row">
                            <label htmlFor="name" className="col-sm-3 col-form-label">Name</label>
                            <input
                                type="text"
                                className="form-control col-sm-9"
                                id="name"
                                required
                                value={this.state.name}
                                onChange={this.onChangeName}
                                name="name"
                            />
                        </div>
                        <div className="form-group row">
                            <label htmlFor="description" className="col-sm-3 col-form-label">Description</label>
                            <input
                                type="text"
                                className="form-control col-sm-9"
                                id="description"
                                required
                                value={this.state.description}
                                onChange={this.onChangeDescription}
                                name="description"
                            />
                        </div>
                        <div className="form-group row">
                            <label htmlFor="price" className="col-sm-3 col-form-label">Price</label>
                            <input
                                type="number"
                                step="0.01"
                                className="form-control col-sm-6"
                                id="price"
                                required
                                value={this.state.price}
                                onChange={this.onChangePrice}
                                name="price"
                            />
                            <div className="col-sm-1"></div>
                            <select 
                                className="form-control col-sm-2"
                                id="priceSymbol"
                                required
                                value={this.state.priceSymbol}
                                onChange={this.onChangePriceSymbol}
                                name="priceSymbol"
                            >
                                {this.state.priceSymbols.map(symbol => (
                                    <option key={symbol}
                                            value={symbol}
                                    >
                                        {symbol}
                                    </option>
                                ))}
                            </select>
                        </div>
                        <div className="form-group row">
                            <label htmlFor="category" className="col-sm-3 col-form-label">Category</label>
                            <select
                                className="form-control col-sm-9"
                                id="category"
                                required
                                value={this.state.category}
                                onChange={this.onChangeCategory}
                                name="category"
                            >
                                {this.state.categories.map(category => (
                                    <option key={category.id}
                                            value={category.id}
                                    >
                                        {category.name}
                                    </option>
                                ))}
                            </select>
                        </div>
                        <input type="submit" value="Submit" className="btn btn-primary" />
                    </form>
                </div>
                )}
            </div>
        );
    };

}

export default AddProduct;