import * as dayjs from 'dayjs';
import { ItemType } from 'app/entities/enumerations/item-type.model';

export interface IInvoiceItemBilling {
  id?: string;
  name?: string | null;
  itemType?: ItemType | null;
  minStep?: number | null;
  maxStep?: number | null;
  quantity?: number | null;
  unitPrice?: number | null;
  discount?: number | null;
  price?: number | null;
  product?: string | null;
  globalQuantity?: number | null;
  untilDate?: dayjs.Dayjs | null;
}

export class InvoiceItemBilling implements IInvoiceItemBilling {
  constructor(
    public id?: string,
    public name?: string | null,
    public itemType?: ItemType | null,
    public minStep?: number | null,
    public maxStep?: number | null,
    public quantity?: number | null,
    public unitPrice?: number | null,
    public discount?: number | null,
    public price?: number | null,
    public product?: string | null,
    public globalQuantity?: number | null,
    public untilDate?: dayjs.Dayjs | null
  ) {}
}

export function getInvoiceItemBillingIdentifier(invoiceItem: IInvoiceItemBilling): string | undefined {
  return invoiceItem.id;
}
