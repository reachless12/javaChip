package com.javachip.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javachip.dao.ProductDAO;
import com.javachip.vo.ProductVO;
import com.javachip.vo.SearchVO;

@Repository
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductDAO productDAO;
	
	@Override
	public List<ProductVO> selectAllProduct(SearchVO searchVO) {
		return productDAO.selectAllProduct(searchVO);
	}
	@Override
	public List<ProductVO> selectProductType(String pType) {
		return productDAO.selectProductType(pType);
	}
	@Override
	public ProductVO selectOneProduct(int pNo) {
		return productDAO.selectOneProduct(pNo);
	}
	@Override
	public int insertProduct(ProductVO productVO) {
		return productDAO.insertProduct(productVO);
	}
	@Override
	public int inputProduct(ProductVO productVO) {
		return productDAO.inputProduct(productVO);
	}
	@Override
	public int outputProduct(ProductVO productVO) {
		return productDAO.outputProduct(productVO);
	}
	@Override
	public int updateProduct(ProductVO productVO) {
		return productDAO.updateProduct(productVO);
	}
	@Override
	public int deleteProduct(int pNo) {
		return productDAO.deleteProduct(pNo);
	}
}