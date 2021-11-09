import { Period } from 'app/entities/enumerations/period.model';

export interface ISettingsInvoiceBilling {
  id?: string;
  global?: string | null;
  subscription?: string | null;
  taxPerCent?: number | null;
  detailSkipped?: boolean | null;
  manualBillingOnly?: boolean | null;
  billingActive?: boolean | null;
  perOrganization?: boolean | null;
  fullyAutomatic?: boolean | null;
  period?: Period | null;
  locale?: string | null;
  paymentTerms?: string | null;
}

export class SettingsInvoiceBilling implements ISettingsInvoiceBilling {
  constructor(
    public id?: string,
    public global?: string | null,
    public subscription?: string | null,
    public taxPerCent?: number | null,
    public detailSkipped?: boolean | null,
    public manualBillingOnly?: boolean | null,
    public billingActive?: boolean | null,
    public perOrganization?: boolean | null,
    public fullyAutomatic?: boolean | null,
    public period?: Period | null,
    public locale?: string | null,
    public paymentTerms?: string | null
  ) {
    this.detailSkipped = this.detailSkipped ?? false;
    this.manualBillingOnly = this.manualBillingOnly ?? false;
    this.billingActive = this.billingActive ?? false;
    this.perOrganization = this.perOrganization ?? false;
    this.fullyAutomatic = this.fullyAutomatic ?? false;
  }
}

export function getSettingsInvoiceBillingIdentifier(settingsInvoice: ISettingsInvoiceBilling): string | undefined {
  return settingsInvoice.id;
}
