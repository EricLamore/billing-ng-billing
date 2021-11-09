import { TypeProduct } from 'app/entities/enumerations/type-product.model';

export interface IConsumptionBilling {
  id?: string;
  organisationId?: string | null;
  organizationName?: string | null;
  ratePlanId?: string | null;
  name?: string | null;
  type?: TypeProduct | null;
  month?: number | null;
  year?: number | null;
  details?: string | null;
  nbUnits?: number | null;
}

export class ConsumptionBilling implements IConsumptionBilling {
  constructor(
    public id?: string,
    public organisationId?: string | null,
    public organizationName?: string | null,
    public ratePlanId?: string | null,
    public name?: string | null,
    public type?: TypeProduct | null,
    public month?: number | null,
    public year?: number | null,
    public details?: string | null,
    public nbUnits?: number | null
  ) {}
}

export function getConsumptionBillingIdentifier(consumption: IConsumptionBilling): string | undefined {
  return consumption.id;
}
