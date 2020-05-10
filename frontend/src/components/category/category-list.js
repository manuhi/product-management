import React from 'react';
import CategoryService from '../../http/category-service'

class CategoryList extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            categories: []
        }
    }

    componentDidMount() {
        
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

    render() {
        return (
            <div>
                <h1>All Categories</h1>
                <br/>
                <table class="table">
                    <thead>
                        <tr>
                            <th scope="col">ID</th>
                            <th scope="col">Name</th>
                        </tr>
                    </thead>
                    <tbody>
                        {this.state.categories.map(category => (
                            <tr>
                                <td>{category.id}</td>
                                <td>{category.name}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        );
    };

}

export default CategoryList;