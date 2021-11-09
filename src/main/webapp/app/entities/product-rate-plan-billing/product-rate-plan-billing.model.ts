export interface IProductRatePlanBilling {
  id?: string;
  commercialName?: string | null;
  base?: number | null;
  product?: string | null;
  productRatePlanCharge?: string | null;
  features?: string | null;
  version?: string | null;
  fixedPrice?: boolean | null;
  standardModel?: boolean | null;
  unitsIncluded?: number | null;
  unitsIncludedPrice?: number | null;
  unitsIncludedDuration?: number | null;
}

export class ProductRatePlanBilling implements IProductRatePlanBilling {
  constructor(
    public id?: string,
    public commercialName?: string | null,
    public base?: number | null,
    public product?: string | null,
    public productRatePlanCharge?: string | null,
    public features?: string | null,
    public version?: string | null,
    public fixedPrice?: boolean | null,
    public standardModel?: boolean | null,
    public unitsIncluded?: number | null,
    public unitsIncludedPrice?: number | null,
    public unitsIncludedDuration?: number | null
  ) {
    this.fixedPrice = this.fixedPrice ?? false;
    this.standardModel = this.standardModel ?? false;
  }
}

export function getProductRatePlanBillingIdentifier(productRatePlan: IProductRatePlanBilling): string | undefined {
  return productRatePlan.id;
}
