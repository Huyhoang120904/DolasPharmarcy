import React, { useState, useRef, useEffect } from "react";
import { FiPlus, FiTrash2, FiArrowLeft, FiImage } from "react-icons/fi";
import { Editor } from "@tinymce/tinymce-react";
import { useNavigate, useParams } from "react-router-dom";
import {
  Form,
  Input,
  Select,
  Checkbox,
  Button,
  Tabs,
  Table,
  message,
  Upload,
  Space,
  DatePicker,
} from "antd";
import { PlusOutlined } from "@ant-design/icons";
import { useAuth } from "../../contexts/AuthContext";
import { uploadImg } from "../../api-services/CloudinaryService";
import { BrandService } from "../../api-services/BrandService";
import { SupplierService } from "../../api-services/SupplierService";
import { CategoryService } from "../../api-services/CategoryService";
import { TargetService } from "../../api-services/TargetService";
import { ProductService } from "../../api-services/ProductService";
import Radio from "antd/es/radio/radio";
import FormItem from "antd/es/form/FormItem";

export default function AddProduct({ update = false }) {
  const navigate = useNavigate();
  const editorRef = useRef(null);
  const [activeTab, setActiveTab] = useState("general");
  const [images, setImages] = useState([]);
  const [form] = Form.useForm();
  const [variants, setVariants] = useState([]);
  const { isAuthenticated } = useAuth();
  const [categories, setCategories] = useState([]);
  const [brands, setBrands] = useState([]);
  const [suppliers, setSuppliers] = useState([]);
  const [targets, setTargets] = useState([]);
  const [isPrimary, setIsPrimary] = useState(0);
  const [selectedVariantId, setSelectedVariantId] = useState(null);
  const { slug } = useParams();
  const [productId, setProductId] = useState();
  //brand
  useEffect(() => {
    const fetchBrands = async () => {
      const brandResponse = await BrandService.getBrands();
      setBrands(brandResponse.result.content);
    };
    fetchBrands();
  }, []);

  //categories
  useEffect(() => {
    const fetchCategories = async () => {
      const reponse = await CategoryService.getCatgories();
      setCategories(reponse.result.content);
    };
    fetchCategories();
  }, []);

  //targets
  useEffect(() => {
    const fetchTargets = async () => {
      const reponse = await TargetService.getTargets();
      setTargets(reponse.result.content);
    };
    fetchTargets();
  }, []);

  useEffect(() => {
    if (images.length <= 1) {
      setIsPrimary(0);
    }
  }, [images]);

  //suppliers
  useEffect(() => {
    const fetchSuppliers = async () => {
      const reponse = await SupplierService.getSuppliers();
      setSuppliers(reponse.result.content);
    };
    fetchSuppliers();
  }, []);

  const handleCancel = () => {
    window.history.back();
  };

  const removeImage = (itemIndex) => {
    setImages((prev) => prev.filter((img, index) => index !== itemIndex));
  };

  const handleChangeImg = ({ fileList }) => {
    setImages(fileList.map((file) => file.originFileObj));
  };

  const handleSetPrimaryImage = (index) => {
    setIsPrimary(index);
  };

  const handleSubmit = async (values) => {
    if (!isAuthenticated) {
      message.error("Vui lòng đăng nhập lại");
      navigate("/login");
      return;
    }

    const imagesResponses = await uploadImg(images, isPrimary);

    values = { ...values, description: form.getFieldValue("description") };

    const request = {
      ...values,
      images: imagesResponses,
      variants: variants,
    };

    console.log(`variants`, variants);

    if (update) {
      const tmpRequest = { ...request, images: productId.images };
      console.log(`request: `, tmpRequest);
      await ProductService.updateProduct(productId.id, request);
    } else {
      await ProductService.addProduct(request);
    }
  };

  useEffect(() => {
    setSelectedVariantId("blah");
  }, []);

  function toInitialValue(product) {
    if (!product) return {};

    console.log({
      id: product.id,
      productName: product.productName,
      sku: product.sku,
      slug: product.slug,
      origin: product.origin || "",
      weight: product.weight || undefined,
      manufacturerName: product.manufacturerName || "",
      productStatus: product.productStatus || "ACTIVE",
      requiresPrescription: product.requiresPrescription || false,
      description: product.description || "",
      ingredients: product.ingredients || "",
      dosage: product.dosage || "",
      warning: product.warning || "",

      // Handle nested fields
      brand: {
        brandName: product.brand?.brandName,
      },
      categoryId: product.category?.id,
      supplierId: product.supplier?.id,

      // Handle target
      target: product.target
        ? {
            targetName: product.target.targetName,
          }
        : undefined,

      // Normalize promotion
      promotion: {
        promotionType: product.promotion?.promotionType || "PERCENTAGE_PRODUCT",
        discountAmount: product.promotion?.discountAmount || 0,
        startDate: product.promotion?.startDate,
        endDate: product.promotion?.endDate,
      },

      // Variants
      variants:
        product.variants?.map((v) => ({
          id: v.id,
          name: v.name,
          stock: v.stock,
          unit: v.unit || "",
          price: v.price,
        })) || [],

      // Images
      images: product.images || [],
    });

    return {
      id: product.id,
      productName: product.productName,
      sku: product.sku,
      slug: product.slug,
      origin: product.origin || "",
      weight: product.weight || undefined,
      manufacturerName: product.manufacturerName || "",
      productStatus: product.productStatus || "ACTIVE",
      requiresPrescription: product.requiresPrescription || false,
      description: product.description || "",
      ingredients: product.ingredients || "",
      dosage: product.dosage || "",
      warning: product.warning || "",

      // Handle nested fields
      brand: {
        brandName: product.brand?.brandName,
      },
      categoryId: product.category?.id,
      supplierId: product.supplier?.id,

      // Handle target
      target: product.target
        ? {
            targetName: product.target.targetName,
          }
        : undefined,

      // Normalize promotion
      promotion: {
        promotionType: product.promotion?.promotionType || "PERCENTAGE_PRODUCT",
        discountAmount: product.promotion?.discountAmount || 0,
        startDate: product.promotion?.startDate,
        endDate: product.promotion?.endDate,
      },

      // Variants
      variants:
        product.variants?.map((v) => ({
          id: v.id,
          name: v.name,
          stock: v.stock,
          unit: v.unit || "",
          price: v.price,
        })) || [],

      // Images
      images: product.images || [],
    };
  }

  //set initial value
  useEffect(() => {
    const fetchProduct = async () => {
      if (slug) {
        const productResponse = await ProductService.getProductsBySlug(slug);
        form.setFieldsValue(toInitialValue(productResponse.result));
        productResponse.result?.variants.forEach((variant) => {
          if (variant.isPrimary) setSelectedVariantId(variant.id);
        });
        setProductId(productResponse.result);
        setVariants(productResponse.result.variants);
      } else {
        form.setFieldsValue({
          productName: "",
          sku: "",
          brand: {
            brandName: undefined,
          },
          categoryId: undefined,
          slug: "",
          promotion: {
            promotionType: "PERCENTAGE_PRODUCT",
            discountAmount: 0,
          },
          stock: { total: 0, lowStockThreshold: 0 },
          priceRange: "",
          origin: "",
          manufacturerName: "",
          target: undefined,
          supplierId: undefined,
          productStatus: "ACTIVE",
          description: "",
          ingredients: "",
          dosage: "",
          warning: "",
          requiresPrescription: false,
          variants: [
            {
              id: "blah",
              name: "",
              sku: "",
              price: "",
              stock: "",
            },
          ],
        });
      }
    };
    fetchProduct();
  }, [slug, form]);

  return (
    <div>
      <div className="flex justify-between items-center mb-6">
        <div className="flex items-center gap-2">
          <Button
            icon={<FiArrowLeft />}
            shape="circle"
            onClick={handleCancel}
            style={{ marginRight: 8 }}
          />
          <h1 className="text-2xl font-bold !mb-0">Thêm sản phẩm mới</h1>
        </div>
      </div>
      <Form form={form} layout="vertical" onFinish={handleSubmit}>
        <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
          <div className="lg:col-span-2 space-y-6">
            <div className="bg-white rounded-lg shadow p-6">
              <div className="space-y-4">
                <Form.Item
                  label="Tên sản phẩm"
                  name="productName"
                  rules={[{ required: true, message: "Nhập tên sản phẩm" }]}
                >
                  <Input placeholder="Nhập tên sản phẩm" />
                </Form.Item>
                <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                  <Form.Item
                    label="Mã SKU"
                    name="sku"
                    rules={[{ required: true, message: "Nhập mã SKU" }]}
                  >
                    <Input placeholder="Mã SKU" />
                  </Form.Item>
                  <Form.Item label="Thương hiệu" name={["brand", "brandName"]}>
                    <Select placeholder="Chọn thương hiệu" allowClear>
                      {brands.map((brand) => (
                        <Select.Option key={brand.id} value={brand.brandName}>
                          {brand.brandName}
                        </Select.Option>
                      ))}
                    </Select>
                  </Form.Item>
                </div>
                <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                  <Form.Item
                    label="Danh mục"
                    name="categoryId"
                    rules={[{ required: true, message: "Chọn danh mục" }]}
                  >
                    <Select placeholder="Chọn danh mục">
                      {categories.map((category) => (
                        <Select.Option key={category.id} value={category.id}>
                          {category.categoryName}
                        </Select.Option>
                      ))}
                    </Select>
                  </Form.Item>
                  <Form.Item label="Slug" name="slug">
                    <Input placeholder="Slug" />
                  </Form.Item>
                </div>
              </div>
            </div>
            <div className="bg-white rounded-lg shadow p-6">
              <Tabs
                activeKey={activeTab}
                onChange={setActiveTab}
                items={[
                  {
                    key: "general",
                    label: "Thông tin chung",
                    children: (
                      <div className="space-y-4">
                        <div className="grid grid-cols-1 md:grid-cols-4 gap-4">
                          <Form.Item
                            label="Loại giảm giá"
                            name={["promotion", "promotionType"]}
                          >
                            <Select>
                              <Select.Option value="PERCENTAGE_PRODUCT">
                                Phần trăm (%)
                              </Select.Option>
                              <Select.Option value="FIXED_AMOUNT_PRODUCT">
                                Số tiền cố định
                              </Select.Option>
                            </Select>
                          </Form.Item>
                          <Form.Item
                            label="Giá trị giảm"
                            name={["promotion", "discountAmount"]}
                          >
                            <Input type="number" min={0} />
                          </Form.Item>

                          <Form.Item
                            label="Ngày bắt đầu"
                            name={["promotion", "startDate"]}
                          >
                            <DatePicker
                              format="YYYY-MM-DD"
                              style={{ width: "100%" }}
                              placeholder="Chọn ngày bắt đầu"
                            />
                          </Form.Item>
                          <Form.Item
                            label="Ngày kết thúc"
                            name={["promotion", "endDate"]}
                          >
                            <DatePicker
                              format="YYYY-MM-DD"
                              style={{ width: "100%" }}
                              placeholder="Chọn ngày kết thúc"
                            />
                          </Form.Item>
                        </div>
                        <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
                          <Form.Item label="Xuất xứ" name="origin">
                            <Input placeholder="Xuất xứ sản phẩm" />
                          </Form.Item>
                          <Form.Item label="Trọng lượng" name="weight">
                            <Select>
                              <Select.Option value="Dưới 100g">
                                Dưới 100g
                              </Select.Option>
                              <Select.Option value="100g-500g">
                                100g-500g
                              </Select.Option>
                              <Select.Option value="500g-1kg">
                                500g-1kg
                              </Select.Option>
                              <Select.Option value="1kg-2kg">
                                1kg-2kg
                              </Select.Option>
                              <Select.Option value="Trên 2kg">
                                Trên 2kg
                              </Select.Option>
                            </Select>
                          </Form.Item>
                        </div>
                        <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
                          <Form.Item
                            label="Đối tượng"
                            name={["target", "targetName"]}
                          >
                            <Select placeholder="Chọn đối tượng">
                              {targets.map((target) => (
                                <Select.Option
                                  key={target.id || target.targetName}
                                  value={target.targetName}
                                >
                                  {target.targetName}
                                </Select.Option>
                              ))}
                            </Select>
                          </Form.Item>
                          <Form.Item
                            label="Nhà cung cấp"
                            name="supplierId"
                            rules={[
                              { required: true, message: "Chọn nhà cung cấp" },
                            ]}
                          >
                            <Select placeholder="Chọn nhà cung cấp" allowClear>
                              {suppliers.map((supplier) => (
                                <Select.Option
                                  key={supplier.id}
                                  value={supplier.id}
                                >
                                  {supplier.supplierName}
                                </Select.Option>
                              ))}
                            </Select>
                          </Form.Item>
                          <Form.Item label="Trạng thái" name="productStatus">
                            <Select>
                              <Select.Option value="ACTIVE">
                                Đang bán
                              </Select.Option>
                              <Select.Option value="OUT_OF_STOCK">
                                Hết hàng
                              </Select.Option>
                              <Select.Option value="OUT_OF_BUSINESS">
                                Ngừng kinh doanh
                              </Select.Option>
                              <Select.Option value="INACTIVE">
                                Ngừng bán
                              </Select.Option>
                            </Select>
                          </Form.Item>
                        </div>
                      </div>
                    ),
                  },
                  {
                    key: "variants",
                    label: "Phân loại",
                    children: (
                      <div className="space-y-4">
                        <Form.List name="variants">
                          {(fields, { add, remove }) => {
                            return (
                              <>
                                <Radio.Group
                                  value={selectedVariantId}
                                  onChange={(e) =>
                                    setSelectedVariantId(e.target.value)
                                  }
                                  className="w-full"
                                >
                                  {fields.length > 0 ? (
                                    <div className="grid grid-cols-[0.1fr_1fr_1fr_1fr_1fr_0.1fr] gap-2 mb-2 font-medium">
                                      <div>Chọn</div>
                                      <div>Tên biến thể</div>
                                      <div>Số lượng</div>
                                      <div>Đơn giá</div>
                                      <div>Đơn vị</div>
                                      <div></div>
                                    </div>
                                  ) : null}

                                  {fields.map((field) => {
                                    const variantId =
                                      form.getFieldValue([
                                        "variants",
                                        field.name,
                                        "id",
                                      ]) || crypto.randomUUID();

                                    return (
                                      <div
                                        key={field.key}
                                        className="grid grid-cols-[0.9fr_1fr_1fr_1fr_1fr_0.1fr] gap-2 mb-2 items-center"
                                      >
                                        <div className="flex justify-center items-center">
                                          <Radio
                                            value={variantId}
                                            checked={
                                              variantId === selectedVariantId
                                            }
                                            onChange={() =>
                                              setSelectedVariantId(variantId)
                                            }
                                          >
                                            Phân loại chính
                                          </Radio>
                                        </div>

                                        <Form.Item
                                          {...field}
                                          name={[field.name, "name"]}
                                          noStyle
                                        >
                                          <Input placeholder="Tên biến thể" />
                                        </Form.Item>

                                        <Form.Item
                                          {...field}
                                          name={[field.name, "stock"]}
                                          noStyle
                                        >
                                          <Input
                                            type="number"
                                            min={0}
                                            placeholder="Số lượng"
                                          />
                                        </Form.Item>

                                        <Form.Item
                                          {...field}
                                          name={[field.name, "price"]}
                                          noStyle
                                        >
                                          <Input placeholder="Đơn giá" />
                                        </Form.Item>
                                        <Form.Item
                                          {...field}
                                          name={[field.name, "unit"]}
                                          noStyle
                                        >
                                          <Input placeholder="Đơn vị" />
                                        </Form.Item>

                                        <Button
                                          type="link"
                                          danger
                                          onClick={() => {
                                            remove(field.name);
                                            if (
                                              form.getFieldValue([
                                                "variants",
                                                field.name,
                                                "id",
                                              ]) === selectedVariantId
                                            ) {
                                              const remainingVariants = form
                                                .getFieldValue("variants")
                                                ?.filter(
                                                  (_, i) => i !== field.name
                                                );
                                              setSelectedVariantId(
                                                remainingVariants?.length > 0
                                                  ? remainingVariants[0].id
                                                  : null
                                              );
                                            }
                                          }}
                                        >
                                          <FiTrash2 />
                                        </Button>
                                      </div>
                                    );
                                  })}
                                </Radio.Group>
                                {fields.length === 0 && (
                                  <div className="text-center text-gray-500 py-4">
                                    Chưa có phân loại nào. Vui lòng thêm phân
                                    loại.
                                  </div>
                                )}

                                <Button
                                  type="dashed"
                                  onClick={() => {
                                    const newVariantId = crypto.randomUUID();
                                    add({ id: newVariantId });
                                    if (fields.length === 0) {
                                      setSelectedVariantId(newVariantId);
                                    }
                                  }}
                                  className="w-full mt-2"
                                >
                                  <FiPlus /> Thêm phân loại
                                </Button>
                              </>
                            );
                          }}
                        </Form.List>
                      </div>
                    ),
                  },
                  {
                    key: "description",
                    label: "Mô tả",
                    children: (
                      <Form.Item label="Mô tả sản phẩm" name="description">
                        <Editor
                          apiKey="95zzat4zdhk63cbyepm9apkvb89bqply9apvsjwh88a454sw"
                          onInit={(evt, editor) => (editorRef.current = editor)}
                          value={form.getFieldValue("description")} // controlled by form
                          onEditorChange={(content) => {
                            form.setFieldsValue({
                              description: content,
                            });
                            console.log("Editor content:", content);
                          }}
                          init={{
                            height: 400,
                            menubar: true,
                            plugins: [
                              "advlist",
                              "autolink",
                              "lists",
                              "link",
                              "image",
                              "charmap",
                              "preview",
                              "anchor",
                              "searchreplace",
                              "visualblocks",
                              "code",
                              "fullscreen",
                              "insertdatetime",
                              "media",
                              "table",
                              "code",
                              "help",
                              "wordcount",
                            ],
                            toolbar:
                              "undo redo | blocks | " +
                              "bold italic forecolor | alignleft aligncenter " +
                              "alignright alignjustify | bullist numlist outdent indent | " +
                              "removeformat | help",
                            content_style:
                              "body { font-family:Helvetica,Arial,sans-serif; font-size:14px }",
                          }}
                        />
                      </Form.Item>
                    ),
                  },
                  {
                    key: "medical",
                    label: "Thông tin y tế",
                    children: (
                      <div className="space-y-4">
                        <Form.Item label="Thành phần" name="ingredients">
                          <Input.TextArea
                            rows={3}
                            placeholder="Nhập thành phần sản phẩm"
                          />
                        </Form.Item>
                        <Form.Item label="Liều dùng" name="dosage">
                          <Input.TextArea
                            rows={3}
                            placeholder="Nhập liều dùng khuyến nghị"
                          />
                        </Form.Item>
                        <Form.Item label="Cảnh báo" name="warning">
                          <Input.TextArea
                            rows={3}
                            placeholder="Nhập cảnh báo và chống chỉ định"
                          />
                        </Form.Item>
                        <Form.Item
                          name="requiresPrescription"
                          valuePropName="checked"
                        >
                          <Checkbox>Yêu cầu đơn thuốc</Checkbox>
                        </Form.Item>
                      </div>
                    ),
                  },
                ]}
              />
            </div>
          </div>
          <div className="space-y-6">
            <div className="bg-white rounded-lg shadow p-6">
              <h3 className="text-lg font-medium mb-4">Hình ảnh sản phẩm</h3>
              <div className="space-y-4">
                <Upload
                  onChange={handleChangeImg}
                  listType="picture-card"
                  showUploadList={false}
                  multiple
                  accept="image/*"
                  disabled={images.length >= 5}
                  fileList={images.map((img, idx) => ({
                    uid: idx,
                    name: img.name || `image-${idx}`,
                    status: "done",
                    url: img.url ? img.url : URL.createObjectURL(img),
                    originFileObj: img,
                  }))}
                  beforeUpload={() => false}
                >
                  {images.length < 5 && (
                    <div>
                      <PlusOutlined />
                      <div style={{ marginTop: 8 }}>Tải lên</div>
                    </div>
                  )}
                </Upload>

                <div className="grid grid-cols-2 gap-4">
                  {images.map((image, index) => (
                    <div
                      key={index}
                      className="relative border rounded-md overflow-hidden"
                    >
                      <img
                        src={
                          image?.url // Nếu đã có url từ server
                            ? image?.url
                            : URL.createObjectURL(image) // Nếu là file mới chọn
                        }
                        alt={image.alt}
                        className="w-full h-32 object-cover"
                      />
                      <div className="absolute inset-0 bg-black/40 opacity-0 hover:opacity-100 transition-opacity flex flex-col justify-between p-2">
                        <div className="flex justify-end">
                          <Button
                            type="primary"
                            danger
                            shape="circle"
                            icon={<FiTrash2 />}
                            size="small"
                            onClick={() => removeImage(index)}
                          />
                        </div>
                        <Button
                          type="default"
                          size="small"
                          className="w-full mt-auto"
                          disabled={image.isPrimary}
                          onClick={() => handleSetPrimaryImage(index)}
                        >
                          {isPrimary === index
                            ? "Ảnh chính"
                            : "Đặt làm ảnh chính"}
                        </Button>
                      </div>
                    </div>
                  ))}
                </div>
                <div className="text-sm text-gray-500">
                  <p>Tối đa 5 hình ảnh. Định dạng: JPG, PNG hoặc GIF.</p>
                  <p>Ảnh đầu tiên sẽ được sử dụng làm ảnh chính.</p>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div className="flex justify-end mt-6 space-x-4">
          <Button onClick={handleCancel}>Hủy</Button>
          <Button type="primary" htmlType="submit">
            Lưu sản phẩm
          </Button>
        </div>
      </Form>
    </div>
  );
}
