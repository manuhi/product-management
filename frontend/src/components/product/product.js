import React from 'react';
import ProductService from '../../http/product-service'
import CategoryService from '../../http/category-service'

class Product extends React.Component {

    constructor(props) {
        super(props);
        this.onChangeName = this.onChangeName.bind(this);
        this.onChangeDescription = this.onChangeDescription.bind(this);
        this.onChangePrice = this.onChangePrice.bind(this);
        this.onChangeCategory = this.onChangeCategory.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.getProductById = this.getProductById.bind(this);
        
        this.state = {
            id: null,
            name: "",
            description: "",
            price: "",
            categoryId: "",
            categories: [],
            message: "",
            updated: false
        };
    }

    componentDidMount() {
        this.getProductById(this.props.match.params.id);

        this.categories = CategoryService.getAllCategories()
        
        .then(response => {
            this.setState({
                categories: response.data
            });
        })
        
        .catch(e => {
            console.log(e);
        });
    }

    onChangeName(e) {
        this.setState({
            name: e.target.value
        })
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

    onChangeCategory(e) {
        this.setState({
            categoryId: e.target.value
        })
    }

    getProductById(id) {
        ProductService.getProductById(id)
        
        .then(response => {
            this.setState({
                id: response.data.id,
                name: response.data.name,
                description: response.data.description,
                price: response.data.price,
                categoryId: response.data.category.id
            });
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
                symbolKey: "EUR",
                value: this.state.price
            },
            categoryId: this.state.categoryId
        };

        const id = this.state.id;

        ProductService.updateProduct(id, data)
           
        .then(response => {
            this.setState({
                id: response.data.id,
                name: response.data.name,
                updated: true,
                message: "Update succesfully"
            })
            console.log(this.state.id);
        })

        .catch(e => {
            console.log(e);
        });
    }
    render() {
    
        return (
          <div>
              <div className="edit-form">
                <h4>Product</h4>
                <br/>
                <form onSubmit={this.handleSubmit} >
                    <div className="form-group">
                        <label htmlFor="name">Name</label>
                        <input
                            type="text"
                            className="form-control"
                            id="name"
                            required
                            value={this.state.name}
                            onChange={this.onChangeName}
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="description">Description</label>
                        <input
                            type="text"
                            className="form-control"
                            id="description"
                            required
                            value={this.state.description}
                            onChange={this.onChangeDescription}
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="price">Price in EUR</label>
                        <input
                            type="text"
                            className="form-control"
                            id="price"
                            required
                            value={this.state.price}
                            onChange={this.onChangePrice}
                        />
                    </div>
                  <div className="form-group">
                        <label htmlFor="category">Category</label>
                        <select
                            className="form-control col-sm-9"
                            id="category"
                            required
                            value={this.state.categoryId}
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
                    <input type="submit" value="Update" className="btn btn-primary" />
                    <br/><br/>
                    <p style={{color: '#008000'}}>{this.state.message}</p>
                </form>
              </div>
          </div>
        );
    }

}

export default Product;