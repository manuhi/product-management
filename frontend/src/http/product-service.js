import http from './common'

class ProductService {

    getAllProducts() {
      return http.get("/product");
    }
  
    getProductById(id) {
      return http.get(`/product/${id}`);
    }
  
    saveProduct(product) {
      return http.post("/product", product);
    }
  
    updateProduct(id, product) {
      return http.put(`/product/${id}`, product);
    }
  
    deleteProductById(id) {
      return http.delete(`/product/${id}`);
    }
  
  }
  
  export default new ProductService();
  