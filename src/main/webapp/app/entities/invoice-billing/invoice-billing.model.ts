import * as dayjs from 'dayjs';
import { InvoiceType } from 'app/entities/enumerations/invoice-type.model';
import { Status } from 'app/entities/enumerations/status.model';

export interface IInvoiceBilling {
  id?: string;
  humanId?: string | null;
  type?: InvoiceType | null;
  customerId?: string | null;
  customerName?: string | null;
  month?: number | null;
  year?: number | null;
  emissionDate?: dayjs.Dayjs | null;
  items?: string | null;
  vat?: number | null;
  dueDate?: dayjs.Dayjs | null;
  dateToSend?: dayjs.Dayjs | null;
  sendDate?: dayjs.Dayjs | null;
  dunningSendDate?: dayjs.Dayjs | null;
  creditNoteSendDate?: dayjs.Dayjs | null;
  status?: Status | null;
  comments?: string | null;
  pvalidationDate?: dayjs.Dayjs | null;
  validatorId?: string | null;
  payment?: string | null;
  paymentsHistoric?: string | null;
  paymentMethod?: string | null;
  refund?: string | null;
  purchaseOrder?: string | null;
  message?: string | null;
  additionalItems?: string | null;
  totalPriceGross?: number | null;
  refundAmount?: number | null;
}

export class InvoiceBilling implements IInvoiceBilling {
  constructor(
    public id?: string,
    public humanId?: string | null,
    public type?: InvoiceType | null,
    public customerId?: string | null,
    public customerName?: string | null,
    public month?: number | null,
    public year?: number | null,
    public emissionDate?: dayjs.Dayjs | null,
    public items?: string | null,
    public vat?: number | null,
    public dueDate?: dayjs.Dayjs | null,
    public dateToSend?: dayjs.Dayjs | null,
    public sendDate?: dayjs.Dayjs | null,
    public dunningSendDate?: dayjs.Dayjs | null,
    public creditNoteSendDate?: dayjs.Dayjs | null,
    public status?: Status | null,
    public comments?: string | null,
    public pvalidationDate?: dayjs.Dayjs | null,
    public validatorId?: string | null,
    public payment?: string | null,
    public paymentsHistoric?: string | null,
    public paymentMethod?: string | null,
    public refund?: string | null,
    public purchaseOrder?: string | null,
    public message?: string | null,
    public additionalItems?: string | null,
    public totalPriceGross?: number | null,
    public refundAmount?: number | null
  ) {}
}

export function getInvoiceBillingIdentifier(invoice: IInvoiceBilling): string | undefined {
  return invoice.id;
}
