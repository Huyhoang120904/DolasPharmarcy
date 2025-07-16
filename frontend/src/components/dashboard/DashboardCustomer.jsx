import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { Search, Check, X } from "lucide-react";
import { CustomerService } from "../../api-services/CustomerService";
import { VerificationBadge } from "./VerificationBadge";

const DashboardCustomer = () => {
  const [customers, setCustomers] = useState([]);
  const [totalPages, setTotalPages] = useState();
  const [pagination, setPagination] = useState({
    currentPage: 1,
    itemsPerPage: 10,
  });

  const [filter, setFilter] = useState("");
  const [searchTerm, setSearchTerm] = useState("");
  const arrItemsPerPage = [5, 10, 15, 20];

  useEffect(() => {
    const fetchCustomers = async () => {
      const response = await CustomerService.getAllCustomers();
      setCustomers(response.result.content);
    };
    fetchCustomers();
  }, []);

  const handleSetPage = (page) => {
    setPagination((prev) => ({
      ...prev,
      currentPage: page,
    }));
  };

  // Filter and sort customers
  const filteredCustomers = customers
    .filter((customer) => {
      if (!searchTerm) return true;
      const term = searchTerm.toLowerCase();
      return (
        (customer.name && customer.name.toLowerCase().includes(term)) ||
        (customer.phone && customer.phone.includes(term)) ||
        (customer.email && customer.email.toLowerCase().includes(term))
      );
    })
    .sort((a, b) => {
      switch (filter) {
        case "name_asc":
          return a.name.localeCompare(b.name);
        case "name_desc":
          return b.name.localeCompare(a.name);
        case "newest":
          return new Date(b.createdAt) - new Date(a.createdAt);
        case "oldest":
          return new Date(a.createdAt) - new Date(b.createdAt);
        default:
          return 0;
      }
    });

  return (
    <div className="bg-white px-4 py-3 flex-1">
      <div className="flex justify-between items-center mb-4">
        <div className="flex space-x-4 items-center">
          <div className="relative">
            <input
              type="text"
              placeholder="Tìm kiếm khách hàng..."
              value={searchTerm}
              onChange={(e) => {
                setSearchTerm(e.target.value);
                setPagination((prev) => ({ ...prev, currentPage: 1 }));
              }}
              className="pl-10 pr-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
            <Search className="h-5 w-5 text-gray-400 absolute left-3 top-2.5" />
          </div>
          <select
            value={filter}
            onChange={(e) => setFilter(e.target.value)}
            className="p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
          >
            <option value="">Sắp xếp theo</option>
            <option value="name_asc">Tên A-Z</option>
            <option value="name_desc">Tên Z-A</option>
            <option value="newest">Mới nhất</option>
            <option value="oldest">Cũ nhất</option>
          </select>
        </div>

        <div className="flex items-center gap-2">
          <label className="text-sm text-gray-700">Hiển thị: </label>
          <select
            value={pagination.itemsPerPage}
            className="border rounded-md p-1 text-sm cursor-pointer"
            onChange={(e) =>
              setPagination({
                currentPage: 1,
                itemsPerPage: Number(e.target.value),
              })
            }
          >
            {arrItemsPerPage.map((item, index) => (
              <option value={item} key={index}>
                {item}
              </option>
            ))}
          </select>
          <span className="text-sm text-gray-500">khách hàng / trang</span>
        </div>
      </div>

      <div className="border-x border-gray-200 rounded-md mt-3">
        <table className="min-w-full text-sm text-gray-800 bg-white border border-gray-200 shadow-md rounded-md overflow-hidden">
          <thead className="bg-gray-100 uppercase text-gray-600 text-xs font-semibold sticky top-0 z-10">
            <tr>
              <th className="px-4 py-3 text-left">Họ và tên</th>
              <th className="px-4 py-3 text-left">Email</th>
              <th className="px-4 py-3 text-left">Ngày sinh</th>
              <th className="px-4 py-3 text-left">Ngày tạo</th>
              <th className="px-4 py-3 text-left">Tình trạng xác thực</th>
              <th className="px-4 py-3 text-center">Hành động</th>
            </tr>
          </thead>
          <tbody>
            {customers.map((customer, index) => (
              <tr
                key={customer.id}
                className={
                  index % 2 === 0 ? "bg-white" : "bg-gray-50 hover:bg-gray-100"
                }
              >
                <td className="px-4 py-2 text-blue-600 font-semibold">
                  {customer.userDetail.fullName}
                </td>

                <td className="px-4 py-2 text-gray-600">
                  {customer.userDetail.email || "-"}
                </td>

                <td className="px-4 py-2 text-gray-600">
                  {customer.userDetail.createdAt
                    ? new Date(
                        customer.userDetail.createdAt
                      ).toLocaleDateString("vi-VN")
                    : "-"}
                </td>
                <td className="px-4 py-2 text-gray-600">
                  {customer.userDetail.createdAt
                    ? new Date(
                        customer.userDetail.createdAt
                      ).toLocaleDateString("vi-VN")
                    : "-"}
                </td>
                <td className="px-4 py-2 text-gray-600">
                  <VerificationBadge
                    isVerified={customer.userDetail.verificationStatus}
                  />
                </td>
                <td className="px-4 py-2 text-center">
                  <div className="flex items-center justify-center space-x-2">
                    <Link
                      to={`/dashboard/customer/${customer.id}`}
                      className="inline-block px-3 py-1.5 text-sm font-medium text-blue-600 bg-blue-100 border border-blue-200 rounded-md hover:bg-blue-200 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-1 transition-colors cursor-pointer"
                    >
                      Xem
                    </Link>
                  </div>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {filteredCustomers.length > 0 && (
        <div className="flex justify-between items-center mt-4 text-sm">
          <div>
            Trang {pagination.currentPage} / {totalPages || 1}
          </div>
          <div className="flex gap-1">
            <button
              className="px-2 py-1 border rounded-md disabled:opacity-50 cursor-pointer"
              onClick={() =>
                handleSetPage(Math.max(pagination.currentPage - 1, 1))
              }
              disabled={pagination.currentPage === 1}
            >
              Trước
            </button>
            {Array.from({ length: Math.min(5, totalPages) }, (_, i) => {
              let pageNum;
              if (totalPages <= 5) {
                pageNum = i + 1;
              } else if (pagination.currentPage <= 3) {
                pageNum = i + 1;
              } else if (pagination.currentPage >= totalPages - 2) {
                pageNum = totalPages - 4 + i;
              } else {
                pageNum = pagination.currentPage - 2 + i;
              }
              return (
                <button
                  key={i}
                  className={`px-2 py-1 border rounded-md cursor-pointer ${
                    pagination.currentPage === pageNum
                      ? "bg-blue-500 !text-white"
                      : ""
                  }`}
                  onClick={() => handleSetPage(pageNum)}
                >
                  {pageNum}
                </button>
              );
            })}
            <button
              className="px-2 py-1 border rounded-md disabled:opacity-50 cursor-pointer"
              onClick={() =>
                handleSetPage(Math.min(pagination.currentPage + 1, totalPages))
              }
              disabled={
                pagination.currentPage === totalPages || totalPages === 0
              }
            >
              Sau
            </button>
          </div>
        </div>
      )}
    </div>
  );
};

export default DashboardCustomer;
