package com.nftworlds.wallet.objects;

import com.nftworlds.wallet.NFTWorlds;
import com.nftworlds.wallet.contracts.nftworlds.Players;
import lombok.Getter;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public class NFTPlayer {

    @Getter
    private static HashSet<NFTPlayer> players = new HashSet<>();

    @Getter
    private UUID uuid;
    private List<Wallet> wallets;
    private boolean linked = false;

    @SneakyThrows
    public NFTPlayer(UUID uuid) {
        this.uuid = uuid;

        Players playerContract = NFTWorlds.getInstance().getPlayers();

        String primary = playerContract.getPlayerPrimaryWallet(uuid.toString().replace("-", ""));
        List<String> secondary = playerContract.getPlayerSecondaryWallets(uuid.toString().replace("-", ""));

        if (!primary.equalsIgnoreCase("0x0000000000000000000000000000000000000000")) {
            linked = true;
        }

        wallets = new ArrayList<>();
        wallets.add(new Wallet(uuid, primary));
        for (String wallet : secondary) {
            wallets.add(new Wallet(uuid, wallet));
        }

        players.add(this);
    }

    /**
     * Get a player's wallet(s)
     *
     * @return player's wallet(s)
     */
    public List<Wallet> getWallets() {
        return wallets;
    }

    /**
     * Get a player's primary wallet
     *
     * @return player's wallet
     */
    public Wallet getPrimaryWallet() {
        return wallets.get(0);
    }

    /**
     * Send a request for a WRLD transaction to a player
     *
     * @param amount
     * @param network
     * @param reason
     */
    public void requestWRLD(double amount, Network network, String reason) {
        getPrimaryWallet().requestWRLD(amount, network, reason);
    }

    /**
     * Send WRLD to a player's primary wallet
     *
     * @param amount
     * @param network
     * @param reason
     */
    public void sendWRLD(double amount, Network network, String reason) {
        getPrimaryWallet().payWRLD(amount, network, reason);
    }

    /**
     * Create a peer to peer player payment link
     *
     * @param to
     * @param amount
     * @param network
     * @param reason
     */
    public void createPlayerPayment(NFTPlayer to, double amount, Network network, String reason) {
        getPrimaryWallet().createPlayerPayment(to, amount, network, reason);
    }

    /**
     * Check if player has their wallet linked
     *
     * @return if player has wallet linked
     */
    public boolean isLinked() {
        return linked;
    }

    public static void remove(UUID uuid) {
        players.removeIf(nftPlayer -> nftPlayer.getUuid().equals(uuid));
    }

    public static NFTPlayer getByUUID(UUID uuid) {
        for (NFTPlayer player : players) {
            if (player.getUuid().equals(uuid)) {
                return player;
            }
        }
        return null;
    }

}
