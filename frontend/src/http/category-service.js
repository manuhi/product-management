import http from './common'

class CategoryService {

    getAllCategories() {
      return http.get("/category");
    }
  
    getCategoryById(id) {
      return http.get(`/category/${id}`);
    }
  
    saveCagegory(category) {
      return http.post("/category", category);
    }
  
    updateCategory(id, category) {
      return http.put(`/category/${id}`, category);
    }
  
    deleteCategoryById(id) {
      return http.delete(`/category/${id}`);
    }
  
  }
  
  export default new CategoryService();
  