const cloudName = import.meta.env.VITE_CLOUDINARY_NAME;

export const uploadImg = async (files, isPrimary) => {
  const uploadPromises = files.map((file) => {
    const formData = new FormData();

    formData.append("file", file);
    formData.append("upload_preset", "dolasPharmacy_ProductImages");

    return fetch(`https://api.cloudinary.com/v1_1/${cloudName}/image/upload`, {
      method: "POST",
      body: formData,
    })
      .then((res) => res.json())
      .then((image) => {
        return { ...image, isPrimary: image.isPrimary };
      })
      .catch((e) => console.log(e));
  });

  const result = await Promise.all(uploadPromises);

  const filteredResponse = result.map((img, index) => {
    return {
      url: img.secure_url,
      alt: img.public_id,
      isPrimary: isPrimary === index,
    };
  });

  return filteredResponse;
};
