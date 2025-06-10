import { Pagination } from "antd";

const ProductPagination = ({ pagination, handlePageChange }) => {
  return (
    <Pagination
      current={pagination.page + 1}
      pageSize={pagination.size}
      total={pagination.totalElements}
      onChange={handlePageChange}
      showSizeChanger={false}
      hideOnSinglePage={false}
      className="custom-pagination"
      itemRender={(page, type, originalElement) => {
        if (type === "page") {
          return (
            <div
              className={`pagination-item ${
                pagination.page + 1 === page
                  ? "pagination-item-active bg-blue-600 text-white"
                  : "hover:bg-blue-100"
              }`}
            >
              {page}
            </div>
          );
        }
        if (type === "prev" || type === "next") {
          return <div className="pagination-nav-button">{originalElement}</div>;
        }
        return originalElement;
      }}
    />
  );
};

export default ProductPagination;
