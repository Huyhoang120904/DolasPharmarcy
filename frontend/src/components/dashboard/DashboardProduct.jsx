import React, { useEffect, useState } from "react";
import { ChevronDownIcon } from "@heroicons/react/20/solid";
import TableDashboard from "./TableDashboard";
import { FaPlus } from "react-icons/fa6";
import { Link } from "react-router-dom";
import { CategoryService } from "../../api-services/CategoryService";
const DashboardProduct = () => {
  const [categoryData, setCategoryData] = useState([]);
  const [currentCategory, setCurrentCategory] = useState("");
  const [currentFilter, setCurrentFilter] = useState("");

  const listFilter = [
    { id: "productName", name: "Name A-Z", order: "ASC" },
    { id: "productName", name: "Name Z-A", order: "DESC" },
    { id: "primaryVariantPrice", name: "Price Ascending", order: "ASC" },
    { id: "primaryVariantPrice", name: "Price Descending", order: "DESC" },
  ];

  useEffect(() => {
    const fetchCategory = async () => {
      const response = await CategoryService.getCatgories();
      setCategoryData(response.result.content);
      console.log(response);
    };
    fetchCategory();
  }, []);

  const handleCategoryChange = (e) => {
    setCurrentCategory(e.target.value);
  };

  const handleFilterChange = (e) => {
    setCurrentFilter(e.target.value);
  };

  console.log(currentFilter);

  return (
    <>
      <div className="flex justify-between items-center">
        <h1 className="text-3xl !font-bold">Sản phẩm</h1>
        <button className="bg-[#3782f7] hover:opacity-80 !text-white transition-colors duration-300 px-4 py-2 rounded-sm font-medium flex items-center">
          {/* Thêm sản phẩm */}
          <FaPlus className="mr-2" /> <Link to="add">Thêm sản phẩm</Link>
        </button>
      </div>

      <div className="bg-white py-6 px-4 w-full flex flex-wrap gap-8 mt-5 items-center rounded-md shadow">
        {/* Danh mục sản phẩm */}
        <div className="flex items-center">
          <label className="text-lg font-medium text-gray-700 mr-3">
            Danh mục:{" "}
          </label>
          <select
            value={currentCategory}
            onChange={handleCategoryChange}
            className="ml-4 w-64 rounded-md border border-gray-300 px-3 py-2 shadow-sm focus:outline-none focus:ring-2 focus:ring-indigo-500"
          >
            <option value="">Tất cả</option>
            {categoryData.map((item) => (
              <option value={item.categoryName} key={item.id}>
                {item.categoryName}
              </option>
            ))}
          </select>
        </div>

        {/* Bộ lọc */}
        <div className="flex items-center">
          <label className="text-lg font-medium text-gray-700 mr-3">Lọc:</label>
          <select
            value={currentFilter}
            onChange={handleFilterChange}
            className="ml-4 w-64 rounded-md border border-gray-300 px-3 py-2 shadow-sm focus:outline-none focus:ring-2 focus:ring-indigo-500"
          >
            <option value="">Tất cả</option>
            {listFilter.map((item, index) => (
              <option value={item.id + "," + item.order} key={index}>
                {item.name}
              </option>
            ))}
          </select>
        </div>
      </div>

      <div className="bg-white mt-6 p-6 rounded-md shadow">
        <TableDashboard category={currentCategory} sortObj={currentFilter} />
      </div>
    </>
  );
};

export default DashboardProduct;
