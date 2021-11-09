export interface IProductSettingBilling {
  id?: string;
  name?: string | null;
  value?: string | null;
}

export class ProductSettingBilling implements IProductSettingBilling {
  constructor(public id?: string, public name?: string | null, public value?: string | null) {}
}

export function getProductSettingBillingIdentifier(productSetting: IProductSettingBilling): string | undefined {
  return productSetting.id;
}
