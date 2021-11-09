import * as dayjs from 'dayjs';

export interface IRatePlanBilling {
  id?: string;
  name?: string | null;
  lastUpdate?: dayjs.Dayjs | null;
  description?: string | null;
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
  ratePlanId?: string | null;
  discountUnitPrice?: number | null;
  discountBase?: number | null;
  prorata?: number | null;
  activationDate?: dayjs.Dayjs | null;
  endDate?: dayjs.Dayjs | null;
  ratePlanCharges?: string | null;
  subscriptionFeatures?: string | null;
}

export class RatePlanBilling implements IRatePlanBilling {
  constructor(
    public id?: string,
    public name?: string | null,
    public lastUpdate?: dayjs.Dayjs | null,
    public description?: string | null,
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
    public unitsIncludedDuration?: number | null,
    public ratePlanId?: string | null,
    public discountUnitPrice?: number | null,
    public discountBase?: number | null,
    public prorata?: number | null,
    public activationDate?: dayjs.Dayjs | null,
    public endDate?: dayjs.Dayjs | null,
    public ratePlanCharges?: string | null,
    public subscriptionFeatures?: string | null
  ) {
    this.fixedPrice = this.fixedPrice ?? false;
    this.standardModel = this.standardModel ?? false;
  }
}

export function getRatePlanBillingIdentifier(ratePlan: IRatePlanBilling): string | undefined {
  return ratePlan.id;
}
