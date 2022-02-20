# NFT Worlds WRLD Payments API

Please see usage documentation and get started here: https://dev.nftworlds.com/payments/wrld-payments-api

This readme will be more extensively updated in the next few days.

Java docs reference: https://dev.nftworlds.com/payments-javadoc/index.html

## About continuous integration in this repo

Every time you **push any commit to main**, GitHub will automatically build a jarfile and upload it to Actions.

To **create a new release**, simply create a tag with the format `vX.Y.Z`. For example:
```bash
git tag v0.0.2
git push origin v0.0.2
```
When a new tag is created GitHub will:
1. Build the WRLD Payments API jar.
2. Upload the jar to a public maven repository.
3. Automatically create a [release](https://github.com/NFT-Worlds/WRLD-Payments-API/releases/) so users can download the shaded jar to put in their plugins folder.
