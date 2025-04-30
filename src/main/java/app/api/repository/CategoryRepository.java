//package app.api.repository;
//
//import app.api.entity.Category;
//import app.api.entity.CategoryId;
//import app.api.entity.UserId;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//import java.util.List;
//import java.util.Set;
//
//@Repository
//public interface CategoryRepository extends JpaRepository<Category, CategoryId> {
//  Set<Category> findCategoriesByUserId(Long userId);
//}