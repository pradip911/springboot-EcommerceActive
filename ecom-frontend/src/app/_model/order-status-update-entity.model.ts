import { Product } from "./product.model";

export interface OrderStatusUpdateEntity {
    orderId: number;
    status : string;
}