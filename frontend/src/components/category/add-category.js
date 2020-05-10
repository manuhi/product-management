import React from 'react';
import { Redirect } from 'react-router-dom';
import CategoryService from '../../http/category-service'

class AddCategory extends React.Component {

    constructor(props) {
        super(props);
        this.onChangeName = this.onChangeName.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);

        this.state = {
            id: null,
            name: "",
            submitted: false
        }
    }

    onChangeName(e) {
        this.setState({
            name: e.target.value
        })
    }

    handleSubmit(event) {

        event.preventDefault();

        var data = {
            name: this.state.name
        };

        CategoryService.saveCagegory(data)
            
        .then(response => {
            this.setState({
                id: response.data.id,
                name: response.data.name,
                submitted: true
            })
            console.log(this.state.id);
        })

        .catch(e => {
            console.log(e);
        });
    }

    render() {
        return (
            <div className="submit-form">
                {this.state.submitted ? (
                    <Redirect to="/category"/>
                ) : (
                <div>
                    <h1>Add a Category</h1>
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
                        <input type="submit" value="Submit" className="btn btn-primary" />
                    </form>
                </div>
                )}
            </div>
        );
    };

}

export default AddCategory;