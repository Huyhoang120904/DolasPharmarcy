import React, { useEffect, useState } from "react";
import { notification } from "antd";
import queryString from "query-string";
import { useSearchParams } from "react-router-dom";
import { useFav } from "../contexts/FavouriteContext";
import { useCart } from "../contexts/CartContext";

// Import new components
import ProductBreadcrumb from "../components/product/ProductBreadcrumb";
import CategorySection from "../components/product/CategorySection";
import FilterSidebar from "../components/product/filter/FilterSidebar";
import SortingHeader from "../components/product/SortingHeader";
import ProductGrid from "../components/product/ProductGrid";
import { ProductService } from "../api-services/ProductService";
import { BrandService } from "../api-services/BrandService";
import { TargetService } from "../api-services/TargetService";

const Product = ({ promotion = false }) => {
  const { favList, toggleFavourite } = useFav();
  const { addToCart } = useCart();
  const [catergories, setCatergories] = useState([]);
  const [branding, setBranding] = useState([]);
  const [targets, setTargets] = useState([]);
  const [products, setProducts] = useState([]);
  const [pagination, setPagination] = useState({});
  const [searchParams, setSearchParams] = useSearchParams();

  let initialFilter = {
    page: 1,
    size: 16,
    totalPages: 1,
    productName: null, // ProductName
    sku: null, // SKU filter
    origin: null, // Country of origin filter
    warning: null, // Warning filter
    ingredients: null, // Ingredients filter
    dosage: null, // Dosage filter
    description: null, // Description filter
    usageInstruction: null, // Usage instruction filter
    slug: null, // Slug filter
    requiresPrescription: null, // Prescription requirement filter
    productStatus: "ACTIVE", // Default Product status filter
    supplierName: null, // Supplier name filter
    brandName: null, // Brand name filter
    targetName: null, // Target name filter
    categoryName: searchParams.get("categoryName") || "", // Category name filter
    stockFrom: null, // Minimum stock filter
    stockTo: null, // Maximum stock filter
    discountAmountFrom: null, // Minimum discount filter
    discountAmountTo: null, // Maximum discount filter
    priceFrom: null, // Minimum price filter
    priceTo: null, // Maximum price filter,
  };

  if (promotion) {
    initialFilter = {
      ...initialFilter,
      discount_ne: null,
    };
  }

  if (!initialFilter.categoryName) {
    delete initialFilter.categoryName;
  }

  const [filter, setFilter] = useState(initialFilter);
  const [activeSort, setActiveSort] = useState(null);
  const [loading, setLoading] = useState(false);
  const [api, contextHolder] = notification.useNotification();

  function handleChangeFilter(e, queryParam) {
    switch (queryParam) {
      case "priceRange": {
        if (e.length === 0) {
          setFilter({ ...filter, priceTo: null, priceFrom: null });
        }
        if (e.includes("Dưới 100.000đ")) {
          setFilter({ ...filter, priceTo: 100000 });
        }
        if (e.includes("100.000đ - 200.000đ")) {
          setFilter({ ...filter, priceFrom: 100000, priceTo: 200000 });
        }
        if (e.includes("200.000đ - 300.000đ")) {
          setFilter({ ...filter, priceFrom: 200000, priceTo: 300000 });
        }
        if (e.includes("300.000đ - 500.000đ")) {
          setFilter({ ...filter, priceFrom: 300000, priceTo: 500000 });
        }
        if (e.includes("Trên 500.000đ")) {
          setFilter({ ...filter, priceFrom: 500000 });
        }
        break;
      }

      case "brandName": {
        console.log(e.length === 0);

        if (e.length === 0) {
          setFilter({ ...filter, [queryParam]: null });
        }
        setFilter({ ...filter, [queryParam]: e });
        break;
      }

      case "targetName": {
        console.log(e.length === 0);
        if (e.length === 0) {
          setFilter({ ...filter, [queryParam]: null });
        }
        setFilter({ ...filter, [queryParam]: e });
        break;
      }

      case "weight": {
        setFilter({ ...filter, [queryParam]: e });
        break;
      }
    }
  }

  function handleChangeSort(name, order) {
    setFilter({
      ...filter,
      sort: name + "," + order,
      page: 1,
    });
    setActiveSort({ sort_name: name, order: order });
  }

  function handleDeleteFilter(queryParam, content) {
    setFilter((prevFilter) => {
      const updatedQueryParam = prevFilter[queryParam].filter(
        (item) => item !== content
      );
      return {
        ...prevFilter,
        [queryParam]: updatedQueryParam,
      };
    });
  }

  function handleClearAllFilters() {
    setFilter({
      ...initialFilter,
      page: 1,
    });
    setActiveSort(null);
  }

  function handlePageChange(n) {
    setPagination({ ...pagination, page: n });
    setFilter({ ...filter, page: n });
  }

  function handleAddToCart(item) {
    addToCart(item);

    console.log(item);

    api.success({
      message: "Thêm giỏ hàng thành công",
      description: `${item.product.productName} được thêm vào giỏ hàng thành công!`,
      duration: 1.5,
    });
  }

  async function handleToggleFav(item) {
    await toggleFavourite(item);
  }

  //search product
  useEffect(() => {
    const fetchProducts = async () => {
      try {
        setLoading(true);
        const reponse = await ProductService.searchProducts(filter);

        const data = reponse.result;
        setProducts(data.content);

        setPagination({
          page: data.pageable.pageNumber,
          size: data.pageable.pageSize,
          totalElements: data.totalElements,
        });
      } catch (error) {
        console.log(error);
      } finally {
        setLoading(false);
      }
    };
    fetchProducts();
  }, [filter]);

  //brand
  useEffect(() => {
    const fetchBrands = async () => {
      const brandResponse = await BrandService.getBrands();
      setBranding(brandResponse.result.content.map((brand) => brand.brandName));
    };
    fetchBrands();
  }, []);

  //target
  useEffect(() => {
    const fetchTargets = async () => {
      const targetResponse = await TargetService.getTargets();
      setTargets(
        targetResponse.result.content.map((target) => target.targetName)
      );
    };
    fetchTargets();
  }, []);

  //product
  useEffect(() => {
    const fetchTargets = async () => {
      const targetResponse = await TargetService.getTargets();
      setTargets(
        targetResponse.result.content.map((target) => target.targetName)
      );
    };
    fetchTargets();
  }, []);

  // Update filter from URL params on initial load
  useEffect(() => {
    const urlSearchQuery = searchParams.get("q");
    if (urlSearchQuery && urlSearchQuery !== filter.q) {
      setFilter((prev) => ({ ...prev, q: urlSearchQuery }));
    }
  }, []);

  // Update search params when filter changes
  useEffect(() => {
    const params = queryString.stringify(filter);
    setSearchParams(params);
  }, [filter, setSearchParams]);

  const filterArr = [
    {
      title: "Chọn mức giá",
      options: [
        "Dưới 100.000đ",
        "100.000đ - 200.000đ",
        "200.000đ - 300.000đ",
        "300.000đ - 500.000đ",
        "Trên 500.000đ",
      ],
      queryParam: "priceRange",
    },
    {
      title: "Thương hiệu",
      options: branding,
      queryParam: "brandName",
    },
    {
      title: "Đối tượng",
      options: targets,
      queryParam: "targetName",
    },
  ];

  const sortArr = [
    { name: "Tên A-Z", order: "ASC", sort_name: "productName" },
    { name: "Tên Z-A", order: "DESC", sort_name: "productName" },
    { name: "Hàng mới", order: "DESC", sort_name: "createdDate" },
    {
      name: "Giá thấp đến cao",
      order: "ASC",
      sort_name: "primaryVariantPrice",
    },
    {
      name: "Giá cao xuống thấp",
      order: "DESC",
      sort_name: "primaryVariantPrice",
    },
  ];

  return (
    <div className="bg-gradient-to-br from-blue-50 via-white to-gray-100 min-h-screen py-12">
      <div className="w-[70%] mx-auto px-4">
        {/* Breadcrumb */}
        <ProductBreadcrumb promotion={promotion} />

        {/* Categories */}
        <CategorySection categories={catergories} setFilter={setFilter} />

        <div className="grid grid-cols-1 lg:grid-cols-[300px_1fr] gap-8">
          {/* Filter sidebar */}
          <FilterSidebar
            filterArr={filterArr}
            filter={filter}
            handleChangeFilter={handleChangeFilter}
            handleDeleteFilter={handleDeleteFilter}
            handleClearAllFilters={handleClearAllFilters}
          />

          {/* Context holder for notification */}
          {contextHolder}

          {/* Product section */}
          <div className="product-section">
            {/* Sorting header */}
            <SortingHeader
              sortArr={sortArr}
              handleChangeSort={handleChangeSort}
              activeSort={activeSort}
            />

            {/* Product grid */}
            <ProductGrid
              products={products}
              loading={loading}
              favList={favList}
              handleAddToCart={handleAddToCart}
              handleToggleFav={handleToggleFav}
              pagination={pagination}
              handlePageChange={handlePageChange}
              setFilter={setFilter}
            />
          </div>
        </div>
      </div>
    </div>
  );
};

export default Product;
