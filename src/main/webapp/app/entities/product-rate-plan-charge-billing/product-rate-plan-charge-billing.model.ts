export interface IProductRatePlanChargeBilling {
  id?: string;
  step?: number | null;
  unitPrice?: number | null;
}

export class ProductRatePlanChargeBilling implements IProductRatePlanChargeBilling {
  constructor(public id?: string, public step?: number | null, public unitPrice?: number | null) {}
}

export function getProductRatePlanChargeBillingIdentifier(productRatePlanCharge: IProductRatePlanChargeBilling): string | undefined {
  return productRatePlanCharge.id;
}
