import { FileHandel } from "./file-handel.model";

export interface Product {
    productId: number,
    productName: String,
    productDescription: String,
    productDiscountedPrice: number,
    productActualPrice: number,
    productQuantity: number,
    sellerName: String,
    sellerEmailId: String,
    productImages: FileHandel[],
    couponCode : String
}