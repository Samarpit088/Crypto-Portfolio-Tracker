package com.crypto.portfolio.service.impl;

import com.crypto.portfolio.dto.CryptoHoldingRequest;
import com.crypto.portfolio.dto.CryptoHoldingResponse;
import com.crypto.portfolio.entity.CryptoHolding;
import com.crypto.portfolio.entity.User;
import com.crypto.portfolio.repository.CryptoHoldingRepository;
import com.crypto.portfolio.repository.CryptoPriceRepository;
import com.crypto.portfolio.repository.UserRepository;
import com.crypto.portfolio.service.CryptoHoldingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CryptoHoldingServiceImpl implements CryptoHoldingService {
    private final CryptoHoldingRepository cryptoHoldingRepository;
    private final UserRepository userRepository;
    private final CryptoPriceRepository cryptoPriceRepository;

    @Override
    public CryptoHoldingResponse addHolding(CryptoHoldingRequest request,String email){
        User user = userRepository.findByEmail(email).orElseThrow(()->new RuntimeException("User not found"));

        CryptoHolding cryptoHolding = CryptoHolding.builder()
                .coinName(request.getCoinName())
                .symbol(request.getSymbol())
                .quantityHeld(request.getQuantityHeld())
                .buyPrice(request.getBuyPrice())
                .buyDate(request.getBuyDate())
                .user(user)
                .build();
        CryptoHolding saved = cryptoHoldingRepository.save(cryptoHolding);
        return CryptoHoldingResponse.builder()
                .id(saved.getId())
                .coinName(saved.getCoinName())
                .symbol(saved.getSymbol())
                .quantityHeld(saved.getQuantityHeld())
                .buyPrice(saved.getBuyPrice())
                .buyDate(saved.getBuyDate())
                .build();
    }

    @Override
    public List<CryptoHoldingResponse> getMyHoldings(String email){
        User user = userRepository.findByEmail(email).orElseThrow(()->new RuntimeException("User not found"));

        return cryptoHoldingRepository.findByUser(user)
                .stream()
                .map(holding->{
                    var price = cryptoPriceRepository.findBySymbol(holding.getSymbol()).orElse(null);

                    BigDecimal currentPrice =  price!=null?price.getCurrentPrice():BigDecimal.ZERO;
                    BigDecimal currentValue = holding.getQuantityHeld().multiply(currentPrice);
                    BigDecimal investedValue = holding.getQuantityHeld().multiply(holding.getBuyPrice());
                    BigDecimal profitLoss = currentValue.subtract(investedValue);


                    return CryptoHoldingResponse.builder()
                            .id(holding.getId())
                            .coinName(holding.getCoinName())
                            .symbol(holding.getSymbol())
                            .quantityHeld(holding.getQuantityHeld())
                            .buyPrice(holding.getBuyPrice())
                            .buyDate(holding.getBuyDate())
                            .currentPrice(currentPrice)
                            .currentValue(currentValue)
                            .profitLoss(profitLoss)
                        .build();
                })
                .toList();
    }

    @Override
    public CryptoHoldingResponse updateHolding(Long id,CryptoHoldingRequest request,String email){
        User user = userRepository.findByEmail(email).orElseThrow(()->new RuntimeException("User not found"));
        CryptoHolding holding = cryptoHoldingRepository.findById(id).orElseThrow(()->new RuntimeException("Holding not found"));
        if(!holding.getUser().getId().equals(user.getId())){
            throw new RuntimeException("You are not allowed to update this holding");
        }
        holding.setCoinName(request.getCoinName());
        holding.setSymbol(request.getSymbol());
        holding.setQuantityHeld(request.getQuantityHeld());
        holding.setBuyPrice(request.getBuyPrice());
        holding.setBuyDate(request.getBuyDate());

        CryptoHolding updated = cryptoHoldingRepository.save(holding);
        return CryptoHoldingResponse.builder()
                .id(updated.getId())
                .coinName(updated.getCoinName())
                .symbol(updated.getSymbol())
                .quantityHeld((updated.getQuantityHeld()))
                .buyPrice(updated.getBuyPrice())
                .buyDate(updated.getBuyDate())
                .build();
    }

    @Override
    public void deleteHolding(Long id,String email){
        User user = userRepository.findByEmail(email).orElseThrow(()->new RuntimeException("User not found"));
        CryptoHolding holding = cryptoHoldingRepository.findById(id).orElseThrow(()->new RuntimeException("Holding not found"));
        if(!holding.getUser().getId().equals(user.getId())){
            throw new RuntimeException("You are not allowed to delete this holding");
        }
        cryptoHoldingRepository.delete(holding);
    }

    @Override
    public Page<CryptoHoldingResponse> getMyHoldingsPaginated(String email,int page,int size){
        User user = userRepository.findByEmail(email).orElseThrow(()-> new RuntimeException("user not found"));
        Pageable pageable = PageRequest.of(page,size);
        return cryptoHoldingRepository.findByUser(user,pageable)
                .map(holding->{
                    var price = cryptoPriceRepository.findBySymbol(holding.getSymbol()).orElse(null);

                    BigDecimal currentPrice = price!=null?price.getCurrentPrice():BigDecimal.ZERO;
                    BigDecimal currentValue = holding.getQuantityHeld().multiply(currentPrice);
                    BigDecimal investedValue = holding.getQuantityHeld().multiply(holding.getBuyPrice());
                    BigDecimal profitLoss = currentValue.subtract(investedValue);

                    return CryptoHoldingResponse.builder()
                            .id(holding.getId())
                            .coinName(holding.getCoinName())
                            .symbol(holding.getSymbol())
                            .quantityHeld(holding.getQuantityHeld())
                            .buyPrice(holding.getBuyPrice())
                            .buyDate(holding.getBuyDate())
                            .currentPrice(currentPrice)
                            .currentValue(currentValue)
                            .profitLoss(profitLoss)
                            .build();
                });
    }
}
