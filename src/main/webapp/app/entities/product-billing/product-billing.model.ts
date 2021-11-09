import * as dayjs from 'dayjs';
import { TypeProduct } from 'app/entities/enumerations/type-product.model';

export interface IProductBilling {
  id?: string;
  name?: string | null;
  lastUpdate?: dayjs.Dayjs | null;
  description?: string | null;
  certificateTypes?: string | null;
  commercialName?: string | null;
  productType?: TypeProduct | null;
  settings?: string | null;
  matrix?: number | null;
}

export class ProductBilling implements IProductBilling {
  constructor(
    public id?: string,
    public name?: string | null,
    public lastUpdate?: dayjs.Dayjs | null,
    public description?: string | null,
    public certificateTypes?: string | null,
    public commercialName?: string | null,
    public productType?: TypeProduct | null,
    public settings?: string | null,
    public matrix?: number | null
  ) {}
}

export function getProductBillingIdentifier(product: IProductBilling): string | undefined {
  return product.id;
}
