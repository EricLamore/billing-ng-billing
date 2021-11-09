jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { SubscriptionFeatureBillingService } from '../service/subscription-feature-billing.service';

import { SubscriptionFeatureBillingDeleteDialogComponent } from './subscription-feature-billing-delete-dialog.component';

describe('SubscriptionFeatureBilling Management Delete Component', () => {
  let comp: SubscriptionFeatureBillingDeleteDialogComponent;
  let fixture: ComponentFixture<SubscriptionFeatureBillingDeleteDialogComponent>;
  let service: SubscriptionFeatureBillingService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [SubscriptionFeatureBillingDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(SubscriptionFeatureBillingDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SubscriptionFeatureBillingDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(SubscriptionFeatureBillingService);
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
