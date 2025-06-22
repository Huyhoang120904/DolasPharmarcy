import queryString from "query-string";
import request, { getMyToken } from "../utils/axiosConfig";

export const ProductService = {
  getProducts: async (queryParams) => {
    try {
      const response = await request.get(
        `/products?${queryParams ? queryParams : ""}`
      );
      return response.data;
    } catch (error) {
      console.log(error);
    }
  },
  getProductsBySlug: async (slug) => {
    try {
      const response = await request.get(`/products/${slug}`);
      return response.data;
    } catch (error) {
      console.log(error);
    }
  },
  searchProducts: async (filter) => {
    try {
      const searchRequest = {
        productName: filter.productName || null, // ProductName
        sku: filter.sku || null, // SKU filter
        origin: filter.origin || null, // Country of origin filter
        warning: filter.warning || null, // Warning filter
        ingredients: filter.ingredients || null, // Ingredients filter
        dosage: filter.dosage || null, // Dosage filter
        description: filter.description || null, // Description filter
        usageInstruction: filter.usageInstruction || null, // Usage instruction filter
        slug: filter.slug || null, // Slug filter
        requiresPrescription: filter.requiresPrescription || null, // Prescription requirement filter
        productStatus: filter.productStatus || null, // Product status filter
        brandName: filter.brandName || null, //Brand name filter
        supplierName: filter.supplierName || null, // Supplier name filter
        targetName: filter.targetName || null, // Target name filter
        categoryName: filter.categoryName || null, // Category name filter

        stockFrom: filter.stockFrom || null, // Minimum stock filter
        stockTo: filter.stockTo || null, // Maximum stock filter

        discountAmountFrom: filter.discountAmountFrom || null, // Minimum discount filter
        discountAmountTo: filter.discountAmountTo || null, // Maximum discount filter

        priceFrom: filter.priceFrom || null, // Minimum price filter
        priceTo: filter.priceTo || null, // Maximum price filter
      };

      const pagination = {
        page: filter.page - 1, // page
        size: filter.size, // Number of items per page
        sort: filter.sort,
      };

      const response = await request.post(
        `/products/search?${queryString.stringify(pagination)}`,
        searchRequest
      );
      return response.data;
    } catch (error) {
      console.log(error);
    }
  },
  addProduct: async (product) => {
    try {
      const response = await request.post("/products", product, {
        headers: {
          Authorization: `Bearer ${getMyToken()}`,
        },
      });

      return response.data;
    } catch (error) {
      console.log(error);
    }
  },
};
