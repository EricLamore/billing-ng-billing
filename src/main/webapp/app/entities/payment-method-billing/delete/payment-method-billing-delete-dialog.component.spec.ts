jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { PaymentMethodBillingService } from '../service/payment-method-billing.service';

import { PaymentMethodBillingDeleteDialogComponent } from './payment-method-billing-delete-dialog.component';

describe('PaymentMethodBilling Management Delete Component', () => {
  let comp: PaymentMethodBillingDeleteDialogComponent;
  let fixture: ComponentFixture<PaymentMethodBillingDeleteDialogComponent>;
  let service: PaymentMethodBillingService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [PaymentMethodBillingDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(PaymentMethodBillingDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PaymentMethodBillingDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(PaymentMethodBillingService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({})));

        // WHEN
        comp.confirmDelete('ABC');
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith('ABC');
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      })
    ));

    it('Should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
